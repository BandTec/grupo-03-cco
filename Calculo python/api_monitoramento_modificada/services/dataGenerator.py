import psutil

intervalo = 2
def getData():
    
    #cria o conjunto que irá receber os dados
    cpu_info = {
    'cpu': 0,
    'memory_total':0,
    'memory': 0,
    'memory_percent':0, 
    'disk': 0,
    'disk_percent': 0
    }

    #leituras

    #cpu = psutil.cpu_percent(interval= intervalo, percpu=True)#gera % de uso das threads da CPU se percpu= True
    cpu_media = psutil.cpu_percent(interval= intervalo, percpu=None) #tira a média do comando acima ou cpu = psutil.cpu_percent(interval=1)
    memory_total = round(((psutil.virtual_memory().total) / pow(1024,3)),1) #retorna a quantidade total de Memória RAM do PC
    memory = round(((psutil.virtual_memory().used) / pow(1024,3)),1) #retorna a quantidade em GB da memória em uso(arredondado pra baixo)
    memory_percent = round(((psutil.virtual_memory().percent)),1) #retorna o uso da memória em %
    disk = round(((psutil.disk_usage('/').used) / pow(1024,3)),1) #retorno o uso do Disco Em GB
    disk_percent = psutil.disk_usage('/').percent #retorna a porcentagem do disco que está sendo usada

    #atribui os valores lidos aos campos do conjunto criado acima

    cpu_info['cpu'] = cpu_media #round(cpu_media,1) 
    cpu_info['memory_total'] = memory_total
    cpu_info['memory'] = memory
    cpu_info['memory_percent'] = memory_percent
    cpu_info['disk'] = disk
    cpu_info['disk_percent'] = disk_percent

    #Objeto para visualização só
    print(cpu_info)
    #lista para envio no banco
    data = (cpu_media, memory_total, memory, memory_percent, disk, disk_percent)

    return data


