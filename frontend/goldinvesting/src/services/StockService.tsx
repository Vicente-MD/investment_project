import axios from 'axios';

export type Quote = {
  exchange: string;
  shortname: string;
  quoteType: string;
  symbol: string;
  index: string;
  score: number;
  typeDisp: string;
  longname: string;
  exchDisp: string;
  sector?: string;
  sectorDisp?: string;
  industry?: string;
  industryDisp?: string;
  dispSecIndFlag?: boolean;
  isYahooFinance: boolean;
};

export type News = {
  uuid: string;
  title: string;
  publisher: string;
  link: string;
  providerPublishTime: number;
  type: string;
  thumbnail?: {
    resolutions: Array<{
      url: string;
      width: number;
      height: number;
      tag: string;
    }>;
  };
  relatedTickers: string[];
};

export type DataResponse = {
  explains: any[];
  count: number;
  quotes: Quote[];
  news: News[];
  nav: any[];
  lists: any[];
  researchReports: any[];
  screenerFieldResults: any[];
  totalTime: number;
  timeTakenForQuotes: number;
  timeTakenForNews: number;
  timeTakenForAlgowatchlist: number;
  timeTakenForPredefinedScreener: number;
  timeTakenForCrunchbase: number;
  timeTakenForNav: number;
  timeTakenForResearchReports: number;
  timeTakenForScreenerField: number;
  timeTakenForCulturalAssets: number;
};

const options = {
  method: 'GET',
  url: 'https://apidojo-yahoo-finance-v1.p.rapidapi.com/auto-complete',
  params: {
    q: 'tesla',
    region: 'US',
  },
  headers: {
    'X-RapidAPI-Key': '463cdc498bmsh8cae7a665a75f49p1088eajsn5b91bdb47077',
    'X-RapidAPI-Host': 'apidojo-yahoo-finance-v1.p.rapidapi.com',
  },
};

export const fetchData = async (): Promise<DataResponse> => {
  try {
    const response = await axios.request(options);
    return response.data;
  } catch (error) {
    console.error(error);
    throw new Error('An error occurred while fetching the data');
  }
};
