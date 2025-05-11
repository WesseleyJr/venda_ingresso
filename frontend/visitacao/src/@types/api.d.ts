export interface Perfil {
  id: number;
}

export interface PostUserData {
  nome: string;
  email: string;
  senha: string;
  confirmaSenha: string;
  perfis: Perfil[];
}

export interface PostLoginData {
  username: string;
  password: string;
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
  id: string;
}

export interface PostDataUser {
  nome: string;
  dataNascimento: string;
  cpf: string;
  telefone: string;
  email: string;
  senha: string;
  id?: string;
}

export interface PropsUserLogin {
  nome: string;
  email: string;
  senha: string;
}

export interface JwtPayload {
  sub: string;
  nome: string;
  id: number;
  role: string;
  exp: number;
}

export type AgendaDTO = {
  capacidade: number;
  dataHora: string;
  id: number;
  ingressos: any[];
  nomeGuia: string;
  preco: number;
};

export type FormDataCliente = {
  nomeCompleto: string;
  celular: string;
  dataNascimento: string;
  nomeResponsavel: string;
  genero: string;
  idUsuario: number;
};

export type FormDataEmailEsqueceuSenha = {
  email: string;
}

export type FormDataRedefinirSenha = {
  token: string;
  novaSenha: string;
  confirmaSenha: string;
}

export type FormDataIngresso = {
  statusIngressoEnum: string,
  idAgenda: number,
  nomeCompleto: string,
  celular: string,
  dataNascimento: string,
  nomeResponsavel: string,
  idUsuario: number,
  idPagamento: number
}

