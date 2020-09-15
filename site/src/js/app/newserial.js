//a variavel sensors recebe o retorno do arquivo sensor.js 
// Isso permite que possamos acessar os código deste arquivo
const sensors = require('./sensors')

// Classe é quase um entidade
// para criar uma classe vc precisará de um constutor, meio que define o que essa classe tem [parecido com o setup do arduino]

class NewArduino {
    //o cosntrutor desta classe cria vetores para serem acessados
    constructor() {
        this.listData = [];
        this.__listDataTemp = [];
        this.listDataHour = [];
    }

    // aqui temos uma função, que recebe a lista de dados
    //obs: é está lista que utilizamos lá no controller
    get List() {
        return this.listData;
    }

    // aqui temos uma função, que recebe a lista de horas
    //obs: é está lista que utilizamos lá no controller
    get ListHour() {
        return this.listDataHour;
    }

    //aqui definimos uma conexão
    SetConnection() {
        //aqui definimos um intervalo de tempo pra gerar os dados, aqui está definido como 3000 Millisegundos, ou seja 3 segundos.
        setInterval(() => {
            //data_float é a variavel que vai receber os dados 'coletados' do sensor 
            //[No caso, os valores gerados pela função ]
            //data_float -> nesta variavel que poderá trocar as funções do arquivo sensor
            //exemplo: let data_float = sensors.lm35();  ou let data_float = sensors.ldr(); etc...
            let data_float = sensors.trc5000();

            if (this.__listDataTemp.length === 59) {

                //variavel sum recebe a classe 'reduzida'ou seja,a função reduce vai percorrer
                //todo o vetor __listDataTemp e somar todos os valores dentro dele, então 
                // a é a varivel com o valor lido e b é o novo valor sendo assim 
                //a sempre será somado ao b 
                let sum = this.__listDataTemp.reduce((a, b) => a + b, 0);

                // divide a variavel sum pelo tamanho da classe fizado em 2 digitos
                //10 /2 -> 5
                this.listDataHour.push((sum / this.__listDataTemp.length).toFixed(2));

                //verifica se o tamanho do vetor __listDataTemp da classe é maior que zero
                while (this.__listDataTemp.length > 0) {
                    //Se for maior remove o ultimo elemento do array e o retorna
                    // pop é uma função js que percorre um vetor e retona o ultimo valor encontrado dentro dele
                
                    this.__listDataTemp.pop();
                }
            }

            // O mentodo push irá percorrer o vetor e adicionar o valor gerado em data_float
            this.__listDataTemp.push(data_float);
            this.listData.push(data_float);

        }, 3000);
    }
}
// instancia da classe new arduino
//instancia é como se fosse uma cópia de um determinado elemento, neste caso a classe NewArduino
const serial = new NewArduino();
//aplica a conexão gerada
serial.SetConnection();
//Exporta um objeto com chave valor
//onde as chaves são list e listHour e os valores são Serial.list e serial.listhour
module.exports.ArduinoDataTemp = { List: serial.List, ListHour: serial.ListHour } 
//para saber mais: https://nodejs.org/api/net.html
