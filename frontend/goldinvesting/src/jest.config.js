module.exports = {
    // ... other configurations
    preset: 'ts-jest',
    testEnvironment: 'node',
    transform: {
      "^.+\\.[t|j]sx?$": "babel-jest",
      "^.+\\.svg$": "jest-transform-stub",
      "^.+\\.css$": "jest-transform-stub",
      '^.+\\.tsx?$': 'babel-jest',
    },
    mduleFileExtensions: ['ts', 'tsx', 'js', 'jsx', 'json', 'node'],
    moduleNameMapper: {
      "\\.(css|less|scss|sass)$": "identity-obj-proxy",
    },
  
    setupFilesAfterEnv: ['<rootDir>/src/setupTests.ts'],
  };
  