valoresLidosCPU =  data.frame(teste$valor, teste$dataTempo,teste$fkComponente)
valoresLidosCPU
subsete = subset(valoresLidosCPU, valoresLidosCPU$teste.fkComponente ==1)
subsete

