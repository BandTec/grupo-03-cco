function simu(){
    var manu = Number(idManutencao.value);
    var publicidade = Number(idPubli.value);
    var lucroManu = manu * 0.10;
    var lucroMin = publicidade * 0.02;
    var lucroMax = publicidade * 0.07;

        idResposta1.innerHTML = `Você economizará R$${lucroManu.toFixed(2)} com gastos de manutenção`;
        idResposta2.innerHTML = `Seu lucro publicitário mínimo será de R$${lucroMin.toFixed(2)}`;
        idResposta3.innerHTML = `Seu lucro publicitário máximo será de R$${lucroMax.toFixed(2)}`;

    
    
}