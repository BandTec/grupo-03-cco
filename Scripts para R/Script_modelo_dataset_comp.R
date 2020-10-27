par(mfrow = c(2,2), mar = c(2,2))
modelo = lm(dataset_comp$cpu~dataset_comp$id)
plot(modelo)
