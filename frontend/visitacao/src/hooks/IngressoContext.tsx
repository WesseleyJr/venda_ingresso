import React, { createContext, useContext, useState, ReactNode } from 'react';

interface IngressoContextType {
  idIngresso: number;
  setIdIngresso: (id: number) => void;
}


const IngressoContext = createContext<IngressoContextType>({
  idIngresso: 0,
  setIdIngresso: () => {},
});

export const IngressoProvider = ({ children }: { children: ReactNode }) => {   
    const [idIngresso, setIdIngresso] = useState<number>(0);

  return (
    <IngressoContext.Provider value={{ idIngresso, setIdIngresso }}>
      {children}
    </IngressoContext.Provider>
  );
};

export const useIngresso = () => useContext(IngressoContext);
