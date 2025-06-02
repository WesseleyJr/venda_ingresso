import React, { createContext, useContext, useState, ReactNode } from 'react';

interface ApiUrlContextType {
  apiUrl: string;
}


const ApiUrlContext = createContext<ApiUrlContextType>({
  apiUrl: '',
});

export const ApiUrlProvider = ({ children }: { children: ReactNode }) => {
  const [apiUrl] = useState<string>('https://711a-2804-56c-8316-7800-4a31-ef74-e5a8-9b37.ngrok-free.app');

  return (
    <ApiUrlContext.Provider value={{ apiUrl }}>
      {children}
    </ApiUrlContext.Provider>
  );
};

export const useApiUrl = () => useContext(ApiUrlContext);
