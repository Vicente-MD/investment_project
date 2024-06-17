import axios, { AxiosInstance, AxiosResponse } from 'axios';

const httpClient: AxiosInstance = axios.create({
  baseURL: 'http://localhost:8080'
});

class Api {
  private apiUrl: string;

  constructor(apiUrl: string) {
    this.apiUrl = apiUrl;
  }

  post<T, R = any>(url: string, object: T): Promise<AxiosResponse<R>> {
    return httpClient.post<R>(`${this.apiUrl}${url}`, object);
  }

  put<T, R = any>(url: string, object: T): Promise<AxiosResponse<R>> {
    return httpClient.put<R>(`${this.apiUrl}${url}`, object);
  }

  delete<R = any>(url: string): Promise<AxiosResponse<R>> {
    return httpClient.delete<R>(`${this.apiUrl}${url}`);
  }

  get<R = any>(url: string): Promise<AxiosResponse<R>> {
    return httpClient.get<R>(`${this.apiUrl}${url}`);
  }
}

export default Api;
