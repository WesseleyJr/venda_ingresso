export interface Cards {
    rua: string;
    numero: string;
    bairro: string;
    dataInicio: string;
    dataFim: string;
    tipo: string;
    id: string;
  }

  export interface PostData {
    rua: string;
    numero: string;
    bairro: string;
    cep: string;
    descricao: string;
    dataInicio: string;
    dataFim: string;
    horaInicio: string;
    horaFim: string;
    tipo: string;
    id?:string
  }

  export interface DataCardPage {
    rua: string;
    numero: string;
    bairro: string;
    descricao: string;
    dataInicio: string;
    dataFim: string;
    horaInicio: string;
    horaFim: string;
    tipo: string;
    id:string
  }

export interface PostDataUser{
    nome: string,
    dataNascimento: string,
    cpf: string,
    telefone: string,
    email: string,
    senha: string,
    id?: string,
  };

  export interface PropsUserLogin{
    nome: string,
    email: string,
    senha: string,
  };