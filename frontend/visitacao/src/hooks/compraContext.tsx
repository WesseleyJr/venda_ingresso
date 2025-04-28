import React, { createContext, useContext, useState, ReactNode } from 'react';

interface CompraContextType {
  qntIngresso: number;
  agendaId: number | null;
  setQntIngresso: (qnt: number) => void;
  setAgendaId: (id: number | null) => void;
}

const CompraContext = createContext<CompraContextType>({
  qntIngresso: 0,
  agendaId: null,
  setQntIngresso: () => {},
  setAgendaId: () => {},
});

interface CompraContextProviderProps {
  children: ReactNode;
}

export const CompraContextProvider = ({ children }: CompraContextProviderProps) => {
  const [qntIngresso, setQntIngresso] = useState<number>(0);
  const [agendaId, setAgendaId] = useState<number | null>(null);

  return (
    <CompraContext.Provider
      value={{
        qntIngresso,
        agendaId,
        setQntIngresso,
        setAgendaId,
      }}
    >
      {children}
    </CompraContext.Provider>
  );
};

export const useCompraContext = () => useContext(CompraContext);
