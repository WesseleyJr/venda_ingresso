import React, { createContext, useContext, useState, ReactNode } from 'react';

interface ApiUrlContextType {
  apiUrl: string;
}


const ApiUrlContext = createContext<ApiUrlContextType>({
  apiUrl: '',
});

export const ApiUrlProvider = ({ children }: { children: ReactNode }) => {
  const [apiUrl] = useState<string>('https://31e0-2804-56c-8316-7800-e595-d17b-2dca-dc83.ngrok-free.app');

  return (
    <ApiUrlContext.Provider value={{ apiUrl }}>
      {children}
    </ApiUrlContext.Provider>
  );
};

export const useApiUrl = () => useContext(ApiUrlContext);
