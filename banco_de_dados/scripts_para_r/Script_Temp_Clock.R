<<<<<<< HEAD
par(mfrow = c(3,2), mar = c(2,2,2,2))
hist(temp_clock$temperatura, main = "histograma de Temperatura",xlab = "Temperatura (�C)", ylab = "Frequência")
barplot(temp_clock$clock, main = "Gr�fico de Clock", xlab = "Clock (MHz)", ylab = "Valor de leitura")
hist(temp_clock$clock ,main = "Gr�fico de Clock", xlab = "Clock (MHz)", ylab = "Valor de leitura")
=======
par(mfrow = c(2,2), mar = c(2,2))
hist(temp_clock$temperatura, main = "histograma de Temperatura",xlab = "Temperatura (�C)", ylab = "Frequência")
barplot(temp_clock$clock, main = "Gr�fico de Clock", xlab = "Clock (MHz)", ylab = "Valor de leitura")
hist(temp_clock$clock ,main = "Gr�fico de Clock", xlab = "Clock (MHz)", ylab = "Valor de leitura")
>>>>>>> a9a12c71bd54c9b55ae63174336b9abb0ade3950
minimo = min(temp_clock$clock)
maximo = max(temp_clock$clock)
barplot(temp_clock$temperatura, main = "Gr�fico de Temperatura",xlab = "Temperatura (�C)", ylab = "Frequência")

<<<<<<< HEAD
curve(x + 0, from = minimo, to = maximo, main = "Gr�fico de clock", xlab = "Clock (MHz)", ylab = "Frequ�ncia")
=======
#curve(x + 0, from = minimo, to = maximo, main = "Gr�fico de clock", xlab = "Clock (MHz)", ylab = "Frequ�ncia")
>>>>>>> a9a12c71bd54c9b55ae63174336b9abb0ade3950
