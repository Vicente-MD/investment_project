module.exports = {
    // ... other configurations
    transform: {
      "^.+\\.[t|j]sx?$": "babel-jest",
      "^.+\\.svg$": "jest-transform-stub",
      "^.+\\.css$": "jest-transform-stub",
    },
    moduleNameMapper: {
      "\\.(css|less|scss|sass)$": "identity-obj-proxy",
    },
  
    setupFilesAfterEnv: ['<rootDir>/src/setupTests.ts'],
  };
  