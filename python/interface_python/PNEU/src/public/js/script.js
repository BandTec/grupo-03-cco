function alterarTela() {
    if(sessionStorage.cargo == "Gerente"){
       var tela2 = "gerente.html";
    }else if(sessionStorage.cargo == "Analista"){
       var tela2 = "tela2.html";
    }
    window.location.href = tela2;
}

function alterarTelaGrafico() {
    var tela3 = "tela_componentes.html";
    window.location.href = tela3;
}

//Funções para uso nos gráficos

function pegarDadosCpu(idServidor) {
    var body = {
        idServidor
    }
    fetch('leitura/returnCPU', {
        method: 'POST',
        headers: {
            'content-Type': 'application/json',
        },
        body: JSON.stringify(body)
    }).then(async response => {
        response.json().then(function (resp) {
            gerarValoresCPU(resp[0], resp[1], resp[2]);
        });

    }).catch(function (err) {
        console.error(err);
    });

}

function pegarDadosMem(idServidor) {
    var body = {
        idServidor
    }
    fetch('leitura/returnMem', {
        method: 'POST',
        headers: {
            'content-Type': 'application/json',
        },
        body: JSON.stringify(body)
    }).then(async response => {
        response.json().then(function (resp) {
                gerarValoresMemoria(resp[0], resp[1]);
            
        });

    }).catch(function (err) {
        console.error(err);
    });
}

function pegarDadosDisco(idServidor) {
    var body = {
        idServidor
    }
    fetch('leitura/returnDisco', {
        method: 'POST',
        headers: {
            'content-Type': 'application/json',
        },
        body: JSON.stringify(body)
    }).then(async response => {
        response.json().then(function (resp) {
                gerarValoresDisco(resp[0], resp[1]);
           
        });

    }).catch(function (err) {
        console.error(err);
    });
}

function pegarDadosLogin() {
    
    var login = document.getElementById("loginInput").value;
    var senha = document.getElementById("senhaInput").value;

    const body = {
        login,
        senha
    }

    fetch('leitura/returnUsuario', {
        method: 'POST',
        headers: {
            'content-Type': 'application/json',
        },
        body: JSON.stringify(body)
    }).then(async response => {

        if (response.ok) {

            response.json().then(function (resp) {
                var jsonLogin = JSON.parse(JSON.stringify(resp[0]));

                if(jsonLogin.Cargo == "Analista"){
                    sessionStorage.cargo = jsonLogin.Cargo;
                    window.location.href="tela2.html";
                }
                else if(jsonLogin.Cargo == "Gerente"){
                    sessionStorage.cargo = jsonLogin.Cargo;
                    window.location.href="gerente.html";
                }
                else{
                    document.getElementById("pErro").innerHTML = "Cargo inválido"
                }

            });
        }
        else{
            document.getElementById("pErro").innerHTML = "Login e/ou senha incorretos"
        }
    }).catch(function (err) {
        console.error(err);
    });
   
}

function retornarGerente(idServidor,cargo){
    const body = {
        idServidor
    }
    console.log(cargo);
    fetch('leitura/returnGerente', {
        method: 'POST',
        headers: {
            'content-Type': 'application/json',
        },
        body: JSON.stringify(body)
    }).then(async response => {
        response.json().then(function (resp) {
            if(cargo == "Analista"){
                document.getElementById("container-servidores").innerHTML += `
                <div class="holder w33">
                    <div class="holder-servidor" onclick="abrirTelaComponente(${idServidor})">
                        <div>
                            <img class="imagem-servidor" src="server.png" alt="Ilustração de um servidor">
                        </div>
                        <div class="texto-servidores">
                            <p class="numero-servidor">
                                Servidor ${idServidor}        
                            </p>
                        </div>
                    </div>              
                </div>
                `
            }else if(cargo == "Gerente"){
                document.getElementById("container-servidores").innerHTML += `
                <div class="box-item-servidor w33">
                    <div class="box-servidor" onclick="abrirTelaComponente(${idServidor})">
                        <p class="pServidor">Servidor ${idServidor}</p>
                        <div class="box-componentes w33">
                            <p class="pComponente">CPU</p>
                            <div class="bola-status" id="cpuStatus${idServidor}"></div>
                            <p class="pComponente" id="cpuPercent${idServidor}">0%</p>
                        </div>
                        <div class="box-componentes w33">
                            <p class="pComponente">Memória</p>
                            <div class="bola-status" id="memStatus${idServidor}"></div>
                            <p class="pComponente" id="memPercent${idServidor}">0%</p>
                        </div>
                        <div class="box-componentes w33">
                            <p class="pComponente">Disco</p>
                            <div class="bola-status" id="disStatus${idServidor}"></div>
                            <p class="pComponente" id="disPercent${idServidor}">0%</p>
                        </div>
                        <div class="clear"></div>
                    </div>
                </div>
                `

                if(resp[0][0] != undefined){
                    var cpu = resp[0][0].valor;
                    document.getElementById(`cpuPercent${idServidor}`).innerHTML = cpu + "%"
                }
                if(resp[1][0] != undefined){
                    var mem = resp[1][0].valor;
                    document.getElementById(`memPercent${idServidor}`).innerHTML = mem + "%"
                }
                if(resp[2][0] != undefined){
                    var disc = resp[2][0].valor;
                    document.getElementById(`disPercent${idServidor}`).innerHTML = disc + "%"
                }
                    
                var cpuHtml = document.getElementById(`cpuStatus${idServidor}`)
                var memHtml = document.getElementById(`memStatus${idServidor}`)
                var discHtml = document.getElementById(`disStatus${idServidor}`)
    
                for(var i = 0;i < 3; i++){
                    if(i == 0){
                        componente = cpu
                        componenteHtml = cpuHtml
                    }else if(i == 1){
                    componente = mem
                    componenteHtml = memHtml 
                    }else if(i == 2){
                    componente = disc
                    componenteHtml = discHtml 
                    }
                    if(componente >= 80){
                        componenteHtml.style.backgroundColor = '#FF5B5B'
                    }else if(componente < 80 && componente > 40){
                        componenteHtml.style.backgroundColor = '#EEFF87'
                    }else if(componente <= 40){
                        componenteHtml.style.backgroundColor = '#A6FF87'
                    }
                }
            }
            
        });
        
        

    }).catch(function (err) {
        console.error(err);
    });
}

function buscarServidores(qntServidor,estado,cargo){
    document.getElementById("container-servidores").innerHTML = `
    <div class="clear"></div>
    <div id="semServidor" class="sem-servidor">
        <div class="sem-servidor-img">
            <img src="Pato.png">
        </div>
        <p>Nenhum servidor foi encontrado</p>
    </div>`
    const body = {
        qntServidor,
        estado
    }
    

    fetch('leitura/returnServidor', {
        method: 'POST',
        headers: {
            'content-Type': 'application/json',
        },
        body: JSON.stringify(body)
    }).then(async response => {

        response.json().then(function (resp) {
            if(resp.length == 0){
                document.getElementById("semServidor").style.display = "block"
            }else{
                for(var i = 0;resp.length > i;i++){
                    
                    retornarGerente(resp[i].idServidor,cargo);
    
                    console.log(resp[i].idServidor);
                }
            }
        });

    }).catch(function (err) {
        console.error(err);
    });

}

function mudarBilling(){
    window.location.href="billing.html";
}

function abrirTelaComponente(idServidor){
    sessionStorage.idServidor = idServidor;
    window.location.href="tela_componentes.html";
}

function logout(){
    sessionStorage.clear()
    window.location.href="index.html"
}