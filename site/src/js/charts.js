/* PEGANDO OS ID's DOS CANVAS */

const chartReal = document.getElementById('graficoReal').getContext("2d"),
    chartSemanal = document.getElementById('graficoSemanal').getContext("2d"),
    chartMensal = document.getElementById('grafMensal').getContext("2d");



chartSemanal.canvas.height="300"
chartMensal.canvas.height="300"


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
window.graficoLinha = new Chart(chartMensal, configMensal);


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
window.graficoLinha = new Chart(chartSemanal, configSemanal);

let configReal = {
    type: 'bar',
    data: {
        labels: ['00:00', '01:00', '02:00', '03:00', '04:00', '05:00', '06:00', '07:00', '08:00', '09:00', '10:00', '11:00', '12:00', '13:00', '14:00', '15:00', '16:00', '17:00', '18:00', '19:00', '20:00', '21:00', '22:00', '23:00'],
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
window.graficoLinha = new Chart(chartReal, configReal);


//PLOTAGEM DO GRAFICO - > COM SERVER

//OBSERVAÇÕES
/*
    SERÁ NECESSÁRIO CRIAR UMA FUNÇÃO QUE CALCULA (ACUMULA) OS VALORES LIDOS NO SENSOR
    E DE ACORDO COM ISSO FAZ A PLOTAGEM DO GRÁFICO. 
    POIS SE DEIXAMOS A LEITURA E PROTAGEM DIRETA, IRÁ APARECER APENAS 1 E 0.
*/

//Função para obter os dados de temperatura do server
//Seria mais interessante fazer isso com WebSocket, porém para fins academicos, os dados serão atualizados via HTTP

//Esse atributo dentro do contexto serve para saber qual foi o último índice processado, assim eliminado os outros elementos na
//hora de montar/atualizar o gráfico
this.lastIndexTemp = 0;
this.time = 0;

function get_data() {

    //variavel que recebe um xml de uma requisição http
    var http = new XMLHttpRequest();
    //http abre o chamado para o método GET na URL 
    http.open('GET', 'http://localhost:3000/api', false);
    //http envia nulo
    http.send(null);

    //obj recebe o texto de resposta transformado em json
    var obj = JSON.parse(http.responseText);
    //exibe no console o objetp
    console.log(obj)
    //verifica se o tamanho do objeto é igual a 0
    if (obj.data.length == 0) {
        return;
    }

    //variavel _lastIndexTemp recebe o ultimo indice desta função 
    var _lastIndexTemp = this.lastIndexTemp;
    //subistitui o ultimo valor pelo tamanho do objeto 
    this.lastIndexTemp = obj.data.length;
    //listtemp recebe o objetocom valores copiados do objeto [do vetor dentro do objeto]
    listTemp = obj.data.slice(_lastIndexTemp);

    listTemp.forEach(data => {
        //let tempSemanal = data * ((((7 * 24) * 60) * 60));
        //let tempMensal = data * (4 * ((((7 * 24) * 60) * 60)));

        //Máximo de 60 itens exibidos no gráfico real
        if (chartReal.data.labels.length == 10 && chartReal.data.datasets[0].data.length == 10) {
            chartReal.data.labels.shift();
            chartReal.data.datasets[0].data.shift();
        }
        //Máximo de 60 itens exibidos no gráfico semanal
        if (chartSemanal.data.labels.length == 10 && chartSemanal.data.datasets[0].data.length == 10) {
            chartSemanal.data.labels.shift();
            chartSemanal.data.datasets[0].data.shift();
        }

        //Máximo de 60 itens exibidos no gráfico mensal
        if (chartMensal.data.labels.length == 10 && chartMensal.data.datasets[0].data.length == 10) {
            chartSemanal.data.labels.shift();
            chartSemanal.data.datasets[0].data.shift();
        }

        //Hora de colocar os dados ❤
        // Aqui começa a montagem dos graficos
        // como possuimos três graficos precisamos fazer três plotagens

        chartReal.data.labels.push(this.time++);
        chartReal.data.datasets[0].data.push(parseFloat(data));
        chartReal.update();

        chartSemanal.data.labels.push(this.time++);
        chartSemanal.data.datasets[0].data.push(parseFloat(tempSemanal));
        chartSemanal.update();

        chartMensal.data.labels.push(this.time++);
        chartMensal.data.datasets[0].data.push(parseFloat(tempMensal));
        chartMensal.update();
    });

    /*
      !PENSE BEM!
      Se um grafico é semanal precisamos do acumulo de dados de  7  dias 
      O mensal, acumulo de 30 dias. 
      Mas nossa variavel acumuladora está em tempo real.
      !COMO RESOLVER!
      Podemos dar um retardo de 7 e 30 dias.
      Podemos Multiplicar só para exemplificar e depois com a conexão no banco, 
      apresentar os dados corretamente
  */

}
/* 
    NOSSO PROJETO RECEBE O ACUMULO DAS LEITURAS.
    POR ESTE MOTIVO .... A LEITURA REALIZADA NÃO PODE SER MOSTRADA DIRETAMENTE NO GRÁFICO
*/

//da start nas funções
get_data();
//Executa as duas funções de 1 em segundo 
setInterval(() => {
    get_data();
}, 1000);

