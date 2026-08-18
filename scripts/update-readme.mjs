import { readFile, writeFile } from 'node:fs/promises';
import { fileURLToPath, pathToFileURL } from 'node:url';

export const START_MARKER = '<!-- START_AUTOMATED_PROJECTS -->';
export const END_MARKER = '<!-- END_AUTOMATED_PROJECTS -->';

function escapeMarkdown(value) {
  return String(value).replace(/[\\`*_[\]<>]/g, '\\$&');
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
    '_Auto-refreshed daily from my public repositories._',
    '',
  ];

  if (projects.length === 0) {
    lines.push('No active first-party repositories found yet.');
    return lines.join('\n');
  }

  for (const project of projects) {
    const language = project.language ? ` · \`${escapeMarkdown(project.language)}\`` : '';
    const description = escapeMarkdown(project.description || 'No description yet.');
    lines.push(`- [**${escapeMarkdown(project.name)}**](${project.html_url})${language} — ${description}`);
  }

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
