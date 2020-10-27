// var botao_tela = document.getElementById("botao_muda_tela");

const { get } = require("../../routes");

// botao_tela.addEventListener('click',alterarTelaServidor());


function alterarTelaServidor(){
    var tela2 = "tela2.html";
    window.location.href = tela2;
}

function alterarTelaGrafico(){
    var tela3 = "tela_componentes.html";
    window.location.href = tela3;
}

//Funções para uso nos gráficos

function pegarDados(){
    fetch('pythonics/return', {
        method: 'get'
    }).then(async function(response) {
        response.json().then(json => {
            //console.log(json);    
        });
    }).catch(function(err) {
        console.error(err);
    });
}



