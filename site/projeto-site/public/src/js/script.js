let qrcode = document.getElementById('qrCodeImg');
let intervalo = 50;//em segundos
var resultado = '';
let repeticao;
let verificacao;

function select(el){
    return document.querySelector(el);
}

function aleatorio(){
    var cod = fazerCod(5)
    generateQR(cod)
}

function generateQR(data) {
    let size = "1000x1000"
    let baseURL = "http://api.qrserver.com/v1/create-qr-code/";

    let url= `${baseURL}?data=${data}&size=${size}`;

    qrcode.src = url;
}

function fazerCod(length) {
    resultado = ''
    var caracteres = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
    for ( var i = 0; i < length; i++ ) {
        resultado += caracteres.charAt(Math.floor(Math.random() * caracteres.length));
    }
    return resultado;
}

function retornar(){
    fetch("codes/returnCode", { 
        method: 'get'
      })
      .then(async function(response) {  

        response.json().then(json =>{
            console.log(json.length);
            if(json.length == 0){
                console.log("Banco vazio");
            }else{
                var codigo = json[0].code
                console.log(codigo);
                if(codigo == resultado){
                    window.location.href = '/perfil.html'
                }else{
                    console.log("Não autenticado")
                }
            }
        })
      })
      .catch(function(err) { console.error(err); });
}

function loopQr(){
    aleatorio()
    repeticao = setInterval(function(){aleatorio()}, intervalo * 1000)
    verificacao = setInterval(function(){retornar()}, intervalo * 100)
}

function requisicao(cod){
    const body = {
        cod
    };

    fetch("codes/storeCode",{
        method: "POST",
        headers: {
         'content-Type': 'application/json'
         },
        body: JSON.stringify(body)
    })
    .then(async response => {

        const res = await response.json();
        if(response.status == 201){
            return console.log("Erro ao enviar");
        }else{
            return console.log("Sucesso ao enviar");
        }
    })
    .catch(res => {Erro('Não conseguimos cadastrar')})
}

function logout(){
    sessionStorage.clear()
    window.location.href = "index.html"
}