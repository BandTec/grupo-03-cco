import requests
import json

print("===== Iniciando API =====")  

class CrawlerOpenHardwareMonitor:
    info = {
        "CPU":0
    }
    def __init__(self):
        self.url = 'http://172.17.175.225:8085/data.json'
        self.data = None
    
    def getJsonData(self):
        response = requests.get(self.url)
        data = json_data = response.json()
        self.data = data
    
    def getInfo(self):
        totalTemp = 0
        totalClock = 0
        
        self.getJsonData()
        clocks = []
        temperatures = []
        
        data = self.data
        for i in data['Children']:
            for desktop in i['Children']:
                #CPU
                if desktop['Text'].find('Intel') >= 0 or desktop['Text'].find('AMD') >= 0:
                    for cpu_metrics in desktop['Children']:
                        # clock
                        if cpu_metrics['Text'] == 'Clocks':
                            for clock in cpu_metrics['Children']:
                                if clock['Text'].find('CPU') >= 0:
                                    clocks.append(clock['Value'])
                        #temperature
                        if cpu_metrics['Text'] == 'Temperatures':
                            for temps in cpu_metrics['Children']:
                                if temps['Text'].find('CPU') >= 0:
                                    temperatures.append(temps['Value'])
            i = 0
            for index, itens in enumerate(clocks):
                i = i + 1
                
                temperature = temperatures[index].replace(",0 °C",".0")
                clock = clocks[index].replace(",",".")
                clock = clock.replace("MHz","")

                totalTemp += float(temperature)
                totalClock += float(clock)
            
            mediaTemp = round((totalTemp/i),2)
            mediaClock = round((totalClock/i),2)
            info = (mediaTemp,mediaClock)
            
            print(info)
            return info


if __name__ == "__main__":
    teste =  CrawlerOpenHardwareMonitor()
    teste.getInfo()
