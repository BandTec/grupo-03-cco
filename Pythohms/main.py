#conexão com o banco de dados e api de avisos do Slack
from services.mysql import Mysql
from services.pythohms import CrawlerOpenHardwareMonitor
import time
#Inserir user, password, host, database
mysql = Mysql('PNEU2SEMESTRE','pneu123', 'localhost', 'projeto')

mysql.connect()
intervalo = 5
while True:
<<<<<<< HEAD
    values = CrawlerOpenHardwareMonitor().getInfo() #pega a variavel info definada em pythohms e atribui a values
=======
    values = CrawlerOpenHardwareMonitor().getInfo() #pega a fvariavel info definada em pythohms e atribui a values
>>>>>>> a9a12c71bd54c9b55ae63174336b9abb0ade3950
    
    print(values)
    mysql.insert(values) #insere values no mysql
    time.sleep(intervalo)