import test from 'node:test';
import assert from 'node:assert/strict';

import {
  buildLatestProjects,
  replaceGeneratedSection,
} from './update-readme.mjs';

const repos = [
  {
    name: 'newest-tool',
    full_name: 'nirmalhk7/newest-tool',
    html_url: 'https://github.com/nirmalhk7/newest-tool',
    description: 'A tool for agentic workflows',
    language: 'TypeScript',
    pushed_at: '2026-08-16T12:00:00Z',
    fork: false,
    archived: false,
  },
  {
    name: 'older-tool',
    full_name: 'nirmalhk7/older-tool',
    html_url: 'https://github.com/nirmalhk7/older-tool',
    description: 'An older project',
    language: 'Python',
    pushed_at: '2026-08-15T12:00:00Z',
    fork: false,
    archived: false,
  },
  {
    name: 'forked-tool',
    full_name: 'nirmalhk7/forked-tool',
    html_url: 'https://github.com/nirmalhk7/forked-tool',
    description: 'Should not be featured',
    language: 'Go',
    pushed_at: '2026-08-17T12:00:00Z',
    fork: true,
    archived: false,
  },
  {
    name: 'nirmalhk7',
    full_name: 'nirmalhk7/nirmalhk7',
    html_url: 'https://github.com/nirmalhk7/nirmalhk7',
    description: 'The profile repository',
    language: null,
    pushed_at: '2026-08-17T13:00:00Z',
    fork: false,
    archived: false,
  },
];

test('builds a compact project grid from active first-party repositories', () => {
  const block = buildLatestProjects(repos, 'nirmalhk7/nirmalhk7', 2);

  assert.match(block, /newest-tool/);
  assert.match(block, /older-tool/);
  assert.ok(block.indexOf('newest-tool') < block.indexOf('older-tool'));
  assert.doesNotMatch(block, /forked-tool|nirmalhk7\/nirmalhk7/);
  assert.match(block, /TypeScript/);
  assert.match(block, /<table>/);
  assert.match(block, /Aug 16/);
  assert.doesNotMatch(block, /A tool for agentic workflows/);
});

test('replaces only the marked generated section', () => {
  const readme = [
    '# Profile',
    '<!-- START_AUTOMATED_PROJECTS -->',
    'old content',
    '<!-- END_AUTOMATED_PROJECTS -->',
    'Keep this editorial copy.',
  ].join('\n');

  const updated = replaceGeneratedSection(readme, 'new content');

  assert.match(updated, /# Profile/);
  assert.match(updated, /new content/);
  assert.match(updated, /Keep this editorial copy\./);
  assert.doesNotMatch(updated, /old content/);
});
