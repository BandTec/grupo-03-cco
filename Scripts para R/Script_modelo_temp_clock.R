par(mfrow = c(2,2), mar = c(2,2))
modelo = lm(temp_clock$clock~temp_clock$id)
plot(modelo)
