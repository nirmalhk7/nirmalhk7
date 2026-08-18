import { readFile, writeFile } from 'node:fs/promises';
import { fileURLToPath, pathToFileURL } from 'node:url';

export const START_MARKER = '<!-- START_AUTOMATED_PROJECTS -->';
export const END_MARKER = '<!-- END_AUTOMATED_PROJECTS -->';

function escapeHtml(value) {
  return String(value).replace(/[&<>"']/g, (character) => ({
    '&': '&amp;',
    '<': '&lt;',
    '>': '&gt;',
    '"': '&quot;',
    "'": '&#39;',
  })[character]);
}

function formatDate(value) {
  return new Intl.DateTimeFormat('en-US', {
    month: 'short',
    day: 'numeric',
    timeZone: 'UTC',
  }).format(new Date(value));
}

export function buildLatestProjects(repositories, profileRepository, limit = 8) {
  const projects = repositories
    .filter((repository) => (
      repository.full_name !== profileRepository
      && !repository.fork
      && !repository.archived
    ))
    .sort((left, right) => (
      new Date(right.pushed_at).getTime() - new Date(left.pushed_at).getTime()
    ))
    .slice(0, limit);

  const lines = [
    '### Latest signals',
    '',
    '<sub>Auto-refreshed daily · active first-party repositories · sorted by recent activity</sub>',
    '',
  ];

  if (projects.length === 0) {
    lines.push('No active first-party repositories found yet.');
    return lines.join('\n');
  }

  lines.push('<table>');

  for (let index = 0; index < projects.length; index += 2) {
    const row = projects.slice(index, index + 2).map((project) => {
      const name = escapeHtml(project.name);
      const url = escapeHtml(project.html_url);
      const language = project.language ? ` · <code>${escapeHtml(project.language)}</code>` : '';
      const updated = formatDate(project.pushed_at);
      return `<td width="50%"><a href="${url}"><b>${name}</b></a><br><sub>${language ? language.slice(3) : 'Repository'} · updated ${updated}</sub></td>`;
    });

    if (row.length === 1) {
      row.push('<td width="50%"></td>');
    }

    lines.push(`<tr>${row.join('')}</tr>`);
  }

  lines.push('</table>');

  return lines.join('\n');
}

export function replaceGeneratedSection(readme, generatedContent) {
  const start = readme.indexOf(START_MARKER);
  const end = readme.indexOf(END_MARKER);

  if (start === -1 || end === -1 || end < start) {
    throw new Error('README.md is missing a valid automated project section.');
  }

  const contentStart = start + START_MARKER.length;
  return [
    readme.slice(0, contentStart),
    '\n',
    generatedContent.trim(),
    '\n',
    readme.slice(end),
  ].join('');
}

async function fetchPublicRepositories(owner, token) {
  const headers = {
    Accept: 'application/vnd.github+json',
    'User-Agent': 'nirmalhk7-profile-readme-updater',
    'X-GitHub-Api-Version': '2022-11-28',
  };

  if (token) {
    headers.Authorization = `Bearer ${token}`;
  }

  const response = await fetch(
    `https://api.github.com/users/${encodeURIComponent(owner)}/repos?per_page=100&type=owner&sort=pushed`,
    { headers },
  );

  if (!response.ok) {
    throw new Error(`GitHub repository lookup failed: ${response.status} ${response.statusText}`);
  }

  return response.json();
}

async function main() {
  const [owner] = (process.env.GITHUB_REPOSITORY || '').split('/');
  const token = process.env.GITHUB_TOKEN;

  if (!owner) {
    throw new Error('GITHUB_REPOSITORY is required.');
  }

  const profileRepository = process.env.GITHUB_REPOSITORY;
  const readmePath = process.env.README_PATH || 'README.md';
  const repositories = await fetchPublicRepositories(owner, token);
  const generatedContent = buildLatestProjects(repositories, profileRepository);
  const readme = await readFile(readmePath, 'utf8');
  const updatedReadme = replaceGeneratedSection(readme, generatedContent);

  if (updatedReadme !== readme) {
    await writeFile(readmePath, updatedReadme);
    console.log(`Updated ${readmePath} from ${repositories.length} public repositories.`);
  } else {
    console.log(`${readmePath} is already current.`);
  }
}

const invokedScript = process.argv[1] && pathToFileURL(process.argv[1]).href;
if (import.meta.url === invokedScript || fileURLToPath(import.meta.url) === process.argv[1]) {
  main().catch((error) => {
    console.error(error.message);
    process.exitCode = 1;
  });
}
