// setupTests.ts
import '@testing-library/jest-dom/extend-expect';

// Mocking the PieChart component globally
jest.mock('@mui/x-charts/PieChart', () => ({
  PieChart: jest.fn().mockImplementation(({ children }) => children),
}));
jest.mock('axios');