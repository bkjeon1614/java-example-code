module.exports = {
  cacheDirectory: '.cache/jest',
  testMatch: ['**/src/**/(*.)(test|spec).ts'],
  transform: {
    '^.+\\.css$': '<rootDir>/config/jest/cssTransform.js',
    '^.+\\.(t)sx?$': '<rootDir>/config/jest/typescriptTransform.js',
    '^(?!.*\\.(js|jsx|css|json)$)': '<rootDir>/config/jest/fileTransform.js',
  },
  coverageReporters: ['html', 'clover'],
  bail: true,
  verbose: true,
  testEnvironment: 'node',
  moduleDirectories: ['node_modules', 'src'],
  moduleFileExtensions: ['ts', 'tsx', 'js', 'json'],
  globals: {
    'ts-jest': {
      tsConfigFile: 'tsconfig.test.json',
    },
  },
  coverageDirectory: 'coverage',
  collectCoverageFrom: ['src/**/*.{ts,tsx}', '!**/__tests__/**', '!**/src/**/(*.)(test|spec|d).ts'],
}
