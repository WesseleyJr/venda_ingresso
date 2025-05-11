import {
  AnimatableNumericValue,
  Text,
  TextInput,
  TouchableOpacity,
  View,
} from "react-native";
import { styles } from "./style";
import { SelectMes } from "../../components/SelectMes";
import { Header } from "../../components/Header";
import { HeaderReturn } from "../../components/HeaderReturn";
import { useEffect, useState } from "react";
import { getAgendaDia, getAgendaMes } from "../../service/agendaService";
import { useApiUrl } from "../../hooks/ApiUrlContext";
import { AgendaDTO } from "../../@types/api";
import Toast from "react-native-toast-message";
import { SelectDia } from "../../components/SelectDia";
import { SelectHora } from "../../components/SelectHora";
import { TextInputField } from "../../components/TextInput";
import { ButtonType } from "../../components/ButtonType";
import { SelectQntIngresso } from "../../components/SelectQntIngresso";
import { useCompraContext } from "../../hooks/compraContext";
import { useNavigation } from "@react-navigation/native";

export const Agenda = () => {
  const { apiUrl } = useApiUrl();
  const {setQntIngresso, setAgendaId} = useCompraContext();
  const [mesSelect, setMesSelect] = useState<number>(0);
  const [diaSelect, setDiaSelect] = useState<number>(0);
  const [horaSelect, setHoraSelect] = useState<string>('');
  const [diasList, setDiasList] = useState<number[]>([]);
  const [horasList, setHorasList] = useState<string[]>([]);
  const [vagas, setVagas] = useState<number>(0);
  const [datasDisponiveis, setDatasDisponiveis] = useState<AgendaDTO[]>([]);
  const navigation = useNavigation()

  const handleMesSelect = async (month: string) => {
    const monthNumber = Number(month);

    try {
      const response = await getAgendaMes(monthNumber, apiUrl);
      setMesSelect(monthNumber);
      onlyDias(response);
    } catch (error: any) {
      Toast.show({
        type: "error",
        text1: "Erro ao buscar datas disponiveis nesse mes",
        visibilityTime: 4000,
      });
    }
  };

  const onlyDias = (datas: AgendaDTO[]) => {
    const diasSet: Set<number> = new Set();
    const dataAtual = new Date();
    const diaAtual = dataAtual.getDate();
    const mesAtual = dataAtual.getMonth();
    const anoAtual = dataAtual.getFullYear();

    for (let i = 0; i < datas.length; i++) {
      const data = new Date(datas[i].dataHora);
      const dia = data.getDate();
      const mes = data.getMonth();
      const ano = data.getFullYear();

      if (
        ano > anoAtual ||
        (ano === anoAtual && mes > mesAtual) ||
        (ano === anoAtual && mes === mesAtual && dia >= diaAtual)
      ) {
        diasSet.add(dia);
      }
    }

    const diasUnicos = Array.from(diasSet);
    diasUnicos.sort((a, b) => a - b);
    setDiasList(diasUnicos);
  };

  const handleDiaSelect = async (dia: number) => {
    try {
      const response = await getAgendaDia(mesSelect, dia, apiUrl);
      setDiaSelect(dia);
      onlyHoras(response);
      setDatasDisponiveis(response);
    } catch (error: any) {
      Toast.show({
        type: "error",
        text1: "Erro ao buscar datas disponiveis nesse mes",
        visibilityTime: 4000,
      });
    }
  };

  const onlyHoras = (datas: AgendaDTO[]) => {
    const horasSet: Set<string> = new Set();
    const dataAtual = new Date();
    const horaAtual = dataAtual.getHours();
    const minutoAtual = dataAtual.getMinutes();

    for (let i = 0; i < datas.length; i++) {
      const data = new Date(datas[i].dataHora);
      const hora = data.getHours();
      const minuto = data.getMinutes();

      if (
        data.getFullYear() > dataAtual.getFullYear() ||
        (data.getFullYear() === dataAtual.getFullYear() &&
          data.getMonth() > dataAtual.getMonth()) ||
        (data.getFullYear() === dataAtual.getFullYear() &&
          data.getMonth() === dataAtual.getMonth() &&
          data.getDate() > dataAtual.getDate()) ||
        (data.getFullYear() === dataAtual.getFullYear() &&
          data.getMonth() === dataAtual.getMonth() &&
          data.getDate() === dataAtual.getDate() &&
          (hora > horaAtual || (hora === horaAtual && minuto >= minutoAtual)))
      ) {
        const horaFormatada = `${hora}:${minuto < 10 ? `0${minuto}` : minuto}`;
        horasSet.add(horaFormatada);
      }
    }

    setHorasList(Array.from(horasSet));
  };

  const handleHoraSelect = async (hora: string) => {
    const dataFiltrada = datasDisponiveis.find((data) => {
      const dataObj = new Date(data.dataHora);

      const diaData = dataObj.getDate();
      const mesData = dataObj.getMonth() + 1;

      const horaData = String(dataObj.getHours()).padStart(2, "0");
      const minutoData = String(dataObj.getMinutes()).padStart(2, "0");
      const horaCompleta = `${horaData}:${minutoData}`;

      const horaFormatada = hora.padStart(5, "0");
      setHoraSelect(hora)
      return (
        diaData === diaSelect &&
        mesData === mesSelect &&
        horaCompleta === horaFormatada
      );
    });

    if (dataFiltrada) {
      handleVagas(dataFiltrada);
    } else {
      console.log("Nenhuma data encontrada.");
    }
  };

  const handleVagas = (dataFiltrada: AgendaDTO) => {
    setAgendaId(dataFiltrada.id)
    const qntIngressosVendidos = dataFiltrada.ingressos.length;
    const qntIngressosDisponiveis =
      dataFiltrada.capacidade - qntIngressosVendidos;
    setVagas(qntIngressosDisponiveis);
  };

  return (
    <>
      <HeaderReturn handleFunction={()=>navigation.navigate('Home')}/>
      <View style={styles.container}>
        <Text style={styles.h1}>Escolha data e horário da visitação</Text>
        <View style={styles.selectArea}>
          <SelectMes handleFunction={handleMesSelect} />
          {diasList.length > 0 ? (
            <>
              <SelectDia dias={diasList} handleFunction={handleDiaSelect} />
              {horasList.length > 0 ? (
                <>
                  <SelectHora
                    horas={horasList}
                    handleFunction={handleHoraSelect}
                  />
                  {horaSelect && (
                    <>
                    <SelectQntIngresso qntIngresso={vagas} handleFunction={(item)=>setQntIngresso(item)}/>
                      <ButtonType
                        handleFunction={() => {
                          navigation.navigate('Cadastro/ingresso')
                        }}
                        title={"Continuar"}
                        propsBackgroundColor="#661111"
                      />
                    </>
                  )}
                </>
              ) : (
                <Text style={styles.h2}>
                  Ainda não temos horário para esse mês
                </Text>
              )}
            </>
          ) : (
            <Text style={styles.h2}>Ainda não temos agenda para esse mês</Text>
          )}
        </View>
      </View>
    </>
  );
};
