import React, { createContext, useContext, useState, ReactNode } from 'react';
import { FormDataIngresso } from '../@types/api';

interface CompraContextType {
  qntIngresso: number;
  agendaId: number | null;
  ingressos: FormDataIngresso[];
  setQntIngresso: (qnt: number) => void;
  setAgendaId: (id: number | null) => void;
  setIngressos: (data: FormDataIngresso[]) => void;
  valorTotal: number;
  setValorTotal: (total: number) => void;
} 

const CompraContext = createContext<CompraContextType>({
  qntIngresso: 0,
  agendaId: null,
  ingressos: [],
  setQntIngresso: () => {},
  setAgendaId: () => {},
  setIngressos: () => {},
  valorTotal: 0,
  setValorTotal: () => {}, 
});

interface CompraContextProviderProps {
  children: ReactNode;
}

export const CompraContextProvider = ({ children }: CompraContextProviderProps) => {
  const [qntIngresso, setQntIngresso] = useState<number>(0);
  const [agendaId, setAgendaId] = useState<number | null>(null);
  const [valorTotal, setValorTotal] = useState<number>(0);
  const [ingressos, setIngressos] = useState<FormDataIngresso[]>([]);

  return (
    <CompraContext.Provider
      value={{
        qntIngresso,
        agendaId,
        ingressos,
        valorTotal,
        setQntIngresso,
        setAgendaId,
        setIngressos,
        setValorTotal,
      }}
    >
      {children}
    </CompraContext.Provider>
  );
};

export const useCompraContext = () => useContext(CompraContext);
