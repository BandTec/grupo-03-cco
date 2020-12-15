<<<<<<< HEAD
par(mfrow = c(3,2), mar = c(2,2,2,2))
hist(temp_clock$temperatura, main = "histograma de Temperatura",xlab = "Temperatura (ï¿½C)", ylab = "FrequÃªncia")
barplot(temp_clock$clock, main = "Grï¿½fico de Clock", xlab = "Clock (MHz)", ylab = "Valor de leitura")
hist(temp_clock$clock ,main = "Grï¿½fico de Clock", xlab = "Clock (MHz)", ylab = "Valor de leitura")
=======
par(mfrow = c(2,2), mar = c(2,2))
hist(temp_clock$temperatura, main = "histograma de Temperatura",xlab = "Temperatura (ºC)", ylab = "FrequÃªncia")
barplot(temp_clock$clock, main = "Gráfico de Clock", xlab = "Clock (MHz)", ylab = "Valor de leitura")
hist(temp_clock$clock ,main = "Gráfico de Clock", xlab = "Clock (MHz)", ylab = "Valor de leitura")
>>>>>>> a9a12c71bd54c9b55ae63174336b9abb0ade3950
minimo = min(temp_clock$clock)
maximo = max(temp_clock$clock)
barplot(temp_clock$temperatura, main = "Gráfico de Temperatura",xlab = "Temperatura (ºC)", ylab = "FrequÃªncia")

<<<<<<< HEAD
curve(x + 0, from = minimo, to = maximo, main = "Grï¿½fico de clock", xlab = "Clock (MHz)", ylab = "Frequï¿½ncia")
=======
#curve(x + 0, from = minimo, to = maximo, main = "Gráfico de clock", xlab = "Clock (MHz)", ylab = "Frequência")
>>>>>>> a9a12c71bd54c9b55ae63174336b9abb0ade3950
