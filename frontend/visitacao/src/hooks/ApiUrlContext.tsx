import React, { createContext, useContext, useState, ReactNode } from 'react';

interface ApiUrlContextType {
  apiUrl: string;
}


const ApiUrlContext = createContext<ApiUrlContextType>({
  apiUrl: '',
});

export const ApiUrlProvider = ({ children }: { children: ReactNode }) => {
  const [apiUrl] = useState<string>('http://192.168.0.101:8080');

  return (
    <ApiUrlContext.Provider value={{ apiUrl }}>
      {children}
    </ApiUrlContext.Provider>
  );
};

export const useApiUrl = () => useContext(ApiUrlContext);
