let qtdPontos = 0;

const freqAtualizacaoDoGrafico = 4;

const cores = ['#976DD0', '#7d4bc4', '#6530b0', '#4e1c94', '#330d69', '#200645'];

const chartReal = document.getElementById("graficoReal").getContext("2d");
const chartSemanal = document.getElementById("graficoSemanal").getContext("2d");
const chartMensal = document.getElementById("graficoMensal").getContext("2d");

let configReal = {
    type: 'bar',
    data: {
        labels: null,
        datasets: null
    },
    options: {
        responsive: true,
        title: {
            display: true,
            text: 'Gráfico em tempo real'
        },
        tooltips: {
            mode: 'index',
            intersect: false,
        },
        hover: {
            mode: 'nearest',
            intersect: true
        },
        scales: {
            xAxes: [{
                display: true,
                scaleLabel: {
                    display: true,
                    labelString: 'Horário'
                }
            }],
            yAxes: [{
                display: true,
                scaleLabel: {
                    display: true,
                    labelString: 'Quantidade de carros'
                }
            }]
        }
    }
};

let configMensal = {
    type: 'bar',
    data: {
        labels: ['Jan', 'Fev', 'Mar', 'Abr', 'Mai', 'Jun', 'Jul', 'Ago', 'Set', 'Out', 'Nov', 'Dez'],
        datasets: [{
            label: 'Quantidade de Veículos',
            backgroundColor: "#976DD0",
            borderColor: "#976DD0",
            data: [],
            fill: false,
        }]
    },
    options: {
        responsive: true,
        title: {
            display: true,
            text: 'Gráfico Mensal'
        },
        tooltips: {
            mode: 'index',
            intersect: false,
        },
        hover: {
            mode: 'nearest',
            intersect: true
        },
        scales: {
            xAxes: [{
                display: true,
                scaleLabel: {
                    display: true,
                    labelString: 'Meses'
                }
            }],
            yAxes: [{
                display: true,
                scaleLabel: {
                    display: true,
                    labelString: 'Veículos'
                }
            }]
        }
    }
};

let configSemanal = {

    type: 'bar',
    data: {
        labels: ['1ºSem', '2ºSem', '3ºSem', '4ºSem', '5ºSem'],
        datasets: [{
            label: 'Quantidade de Veículos',
            backgroundColor: "#976DD0",
            borderColor: "#976DD0",
            data: [],
            fill: false,
        }]
    },
    options: {
        responsive: true,
        title: {
            display: true,
            text: 'Gráfico Semanal do mês'
        },
        tooltips: {
            mode: 'index',
            intersect: false,
        },
        hover: {
            mode: 'nearest',
            intersect: true
        },
        scales: {
            xAxes: [{
                display: true,
                scaleLabel: {
                    display: true,
                    labelString: 'Semanas'
                }
            }],
            yAxes: [{
                display: true,
                scaleLabel: {
                    display: true,
                    labelString: 'Quantidade de Veículos'
                }
            }]
        }
    }
};

let meuGrafico = new Chart(chartReal, configReal);
let meuGraficoMensal = new Chart(chartMensal, configMensal);
let meuGraficoSemanal = new Chart(chartSemanal, configSemanal);

window.onload = colocarRodovias(1);

//Sem explicações, mas os próximos são similares e possuem comentários 
function colocarRodovias(idCliente) {
    let rodoviasObtidas;

    console.log('Pegando rodovias...');

    fetch(`/leituras/pegarRodoviasBD/${idCliente}`).then(response => {
        console.log('Fetch ok');
        if (response.ok) {
            console.log('Response: OK');
            response.json().then(function (retornoBD) {
                console.log(retornoBD);
                rodoviasObtidas = retornoBD;

                for (var i = 0; i < rodoviasObtidas.length; i++) {
                    console.log('Rodovia da vez: ' + rodoviasObtidas[i].nomeRodovia);
                    divRodovias.innerHTML += `<button onclick=colocarOpcoes(${rodoviasObtidas[i].idRodovia})>${rodoviasObtidas[i].nomeRodovia}</button>`;
                }
            });
        }
    });
    console.log('Depois do fetch :(');
}

function colocarOpcoes(idRodovia) {
    let trechosObtidos;

    console.log('Pegando rodovias...');

    //Se comunica com o Node que então puxa do BD os trechos da rodovia
    fetch(`/leituras/pegarTrechos/${idRodovia}`).then(response => {
        console.log('Fetch ok');

        if (response.ok) {
            console.log('Response: OK');
            response.json().then(function (retornoBD) { //Transformando oq retornou od BD em um json
                trechosObtidos = retornoBD;
                qtdPontos = trechosObtidos.length;
                console.log('Qtd de pontos que vieram do BD: ' + qtdPontos);
                divTrechos.innerHTML = '';

                //Preenchendo a tela com os trechos
                for (var i = 0; i < trechosObtidos.length; i++) {
                    console.log('Trecho da vez: ' + trechosObtidos[i].nomeRodovia);
                    divTrechos.innerHTML += ` <input value="${trechosObtidos[i].idPonto}" id="cbPonto${i}" type="checkBox"> <label for="cbPonto${i}">${trechosObtidos[i].nomePonto}</label>`;
                }

                divTrechos.innerHTML += `<button onclick="mostrarGrafico()">Visualizar</button>`;
            }).finally(function () {
                divTrechos.style.display = 'block';
                divRodovias.style.display = 'none';
            });
        }
    });
    console.log('Depois do fetch :(');
}

function montarGrafico() {
    let pontosSelecionados = [];
    let colunasReal = [];
    let colunasMensal = [];
    let colunasSemanal = [];
    let horas = [];

    //Verifica quais trechos estão selecionados e pega seus IDs
    for (var i = 0; i < qtdPontos; i++) {
        let cbDaVez = document.getElementById(`cbPonto${i}`);
        if (cbDaVez.checked) {
            pontosSelecionados.push(cbDaVez.value);
        }
    }

    console.log(pontosSelecionados);

    //Chama o NODE para que ele retorne as informações sobre os trechos
    fetch('leituras/pegarDadosBD', {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(pontosSelecionados), //Passando os IDs dos pontos
    }).then(function (response) {
        //AQUI OCORRE NA SEQUENCIA DA RESPOSTA DO BD
        if (response.status == 201) { //201 = OK
            console.log('Response okay :D');

            //transforma a resposta do BD em um JSON de Strings
            response.json().then(function (retornoBD) {
                console.log(retornoBD);

                //Define os horários (o select está configurado pra pegar somente os 6 registros do topo)
                for (var y = 6; y > 0; y--) {

                    //Pega o momento mais recente do BD e coloca em nosso fuso horário
                    let horaBD = retornoBD[(y * pontosSelecionados.length) - 1].momento;
                    horaBD = horaBD.replace('Z', '-03:00');

                    //Cria um Date com o que veio do BD
                    let horaPega = new Date(horaBD).toLocaleTimeString();

                    //Adiciona na lista de horas que aparecerão no gráfico
                    horas.push(horaPega);
                }

                //Cria as colunas
                for (var i = 0; i < pontosSelecionados.length; i++) {

                    colunasReal.push({
                        label: retornoBD[i].nomePonto,
                        backgroundColor: cores[i],
                        borderColor: cores[i],
                        data: null,
                        fill: false,
                    });

                    colunasMensal.push({
                        label: retornoBD[i].nomePonto,
                        backgroundColor: cores[i],
                        borderColor: cores[i],
                        data: null,
                        fill: false,
                    });

                    colunasSemanal.push({
                        label: retornoBD[i].nomePonto,
                        backgroundColor: cores[i],
                        borderColor: cores[i],
                        data: null,
                        fill: false,
                    });

                    //Pega os respectivos dados e preenche o campo data (que fica dentro da coluna)
                    let dados = [];
                    for (var x = retornoBD.length - 1; x >= 0; x--) {
                        if (retornoBD[x].fkPonto == pontosSelecionados[i]) {
                            dados.push(retornoBD[x].QtdCarros);
                        }
                    }
                    colunasReal[i].data = dados;
                }
            }).finally(() => {

                //Fim de tudo: transfiro as variáveis criadas para o gráfico
                configReal.data.labels = horas;
                configReal.data.datasets = colunasReal;

                meuGrafico.update();
                graficoReal.style.display = 'block';

                plotarFixos(colunasMensal, colunasSemanal);

                //Começo o looping para atualizar o tempo real
                setTimeout(atualizarGrafico, 10000);
            });
        }
    });
}

function atualizarGrafico() {

    let pontosSelecionados = [];

    //Verifica quais trechos estão selecionados e pega seus IDs
    for (var i = 0; i < qtdPontos; i++) {
        let cbDaVez = document.getElementById(`cbPonto${i}`);
        if (cbDaVez.checked) {
            pontosSelecionados.push(cbDaVez.value);
        }
    }

    //Chamando o NODE para atualizar meus pontos
    fetch('leituras/pegarDadosBD', {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(pontosSelecionados), //Passando os IDs dos pontos
    }).then(function (response) {
        if (response.ok) {
            response.json().then(function (resposta) {
                var dados = resposta;

                plotarGrafico(dados);
            });
        } else {
            console.error('Nenhum dado encontrado ou erro na API');
        }
    })
        .catch(function (error) {
            console.error(`Erro na obtenção dos dados p/ gráfico: ${error.message}`);
        });

    setTimeout(atualizarGrafico, freqAtualizacaoDoGrafico * 5000);

}

function plotarGrafico(dados) {
    console.log('iniciando update do gráfico...');

    //Apaga o registro mais antigo (Sua Label com o horário e os dados de todos os pontos)
    configReal.data.labels.shift();

    for (let i = 0; i < configReal.data.datasets.length; i++) {
        configReal.data.datasets[i].data.shift();
    }

    let novaHora = dados[0].momento;
    novaHora = novaHora.replace('Z', '-03:00');
    let horaConvertida = new Date(novaHora).toLocaleTimeString();

    //Insere um novo horário em sua label e adiciona uma nova informação nos pontos
    configReal.data.labels.push(horaConvertida);

    //Looping para adicionar um dado novo em cada ponto que está na página
    for (let i = 0; i < configReal.data.datasets.length; i++) {

        //Looping para vasculhar o retorno do BD e adicionar o dado em seu ponto respectivo
        for (let x = 0; x < dados.length; x++) {
            if (dados[x].nomePonto == meuGrafico.data.datasets[i].label) {
                configReal.data.datasets[i].data.push(dados[x].QtdCarros);
                break;
            }
        }

    }
    meuGrafico.update();
}

function plotarFixos(colunasMensal, colunasSemanal) {
    console.log('FIXOS');
    let mensagens = [];
    configMensal.data.datasets = colunasMensal;
    configSemanal.data.datasets = colunasSemanal;

    for (let i = 0; i < meuGraficoMensal.data.datasets.length; i++) {
        configMensal.data.datasets[i].data = [];

        for (let x = 0; x < 12; x++) {
            qtdCarros = parseInt(Math.random() * (1800000 - 300000) + 300000);

            configMensal.data.datasets[i].data.push(qtdCarros);
            if (qtdCarros > 1600000) {
                if (qtdCarros > 2000000) {
                    mensagens.push(`<div class="avisosRelatorio lRuim">Maior média já registrada em ${configMensal.data.labels[x]} no ${configMensal.data.datasets[i].label}</label></div>`);
                }
                else {
                    mensagens.push(`<div class="avisosRelatorio lRuim">Média de carros muito alta em ${configMensal.data.labels[x]} no ${configMensal.data.datasets[i].label}</label></div>`);
                }
            }
            else {
                if (qtdCarros < 500000) {
                    if (qtdCarros < 150000) {
                        mensagens.push(`<div class="avisosRelatorio lBom"><label style = "color: #FF5733">Menor média de carros em ${configMensal.data.labels[x]} no ${configMensal.data.datasets[i].label}</label></div>`);
                    }
                    else {
                        mensagens.push(`<div class="avisosRelatorio lBom"><p>Média de carros muito baixa em ${configMensal.data.labels[x]} no ${configMensal.data.datasets[i].label}</p></div>`);
                    }
                }
            }
        }
    }

    for (let i = 0; i < meuGraficoSemanal.data.datasets.length; i++) {
        configSemanal.data.datasets[i].data = [];
        for (let x = 0; x < 12; x++) {
            configSemanal.data.datasets[i].data.push(parseInt(Math.random() * (125000 - 20000) + 100000));
        }
    }

    alertar(mensagens);

    meuGraficoMensal.update();
    meuGraficoSemanal.update();
}

function alertar(mensagens) {
    divStatus.innerHTML = '<h3>Relatórios da via: </h3>';

    if (mensagens.length > 0) {
        for (var i = 0; i < mensagens.length; i++) {
            divStatus.innerHTML += mensagens[i];
        }
    }
    else {
        divStatus.innerHTML += '<span style="green">Tudo normal</span>';
    }
}