/**
 * Created by hp on 02.07.2015.
 */
// В примере описывается получение данных от метеорологической станции (класс WeatherData, рассылатель событий) и
//использование их для вывода на экран (класс CurrentConditionsDisplay, слушатель событий).
//Слушатель регистрируется у наблюдателя с помощью метода registerObserver (при этом слушатель заносится в список observers).
//Регистрация происходит в момент создания объекта currentDisplay, т.к. метод registerObserver применяется в конструкторе.
//При изменении погодных данных вызывается метод notifyObservers, который в свою очередь вызывает метод update
//у всех слушателей, передавая им обновлённые данные.
import java.util.ArrayList;
import java.util.List;

public class WeatherStation
{
    public static void main(String[] args)
    {
        WeatherData weatherData = new WeatherData();

        CurrentConditionsDisplay currentDisplay = new CurrentConditionsDisplay(weatherData);
        CurrentConditionsDisplayEn currentDisplayEn = new CurrentConditionsDisplayEn(weatherData);
        weatherData.setMeasurements(29, 65, 30.4f);
        weatherData.setMeasurements(39, 70, 29.4f);
        weatherData.setMeasurements(42, 72, 31.4f);
    }
}

interface Observer
{
    void update (float temperature, float humidity, float pressure);
}

interface Observable
{
    void registerObserver(Observer o);
    void removeObserver(Observer o);
    void notifyObservers();
}


class WeatherData implements Observable
{
    private List<Observer> observers;
    private float temperature;
    private float humidity;
    private float pressure;

    public WeatherData()
    {
        observers = new ArrayList<Observer>();
    }

    @Override
    public void registerObserver(Observer o)
    {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o)
    {
        observers.remove(o);
    }

    @Override
    public void notifyObservers()
    {
        for (Observer observer : observers)
        {
            observer.update(temperature, humidity, pressure);
        }
    }

    public void setMeasurements(float temperature, float humidity, float pressure)
    {
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        notifyObservers();
    };
}

class CurrentConditionsDisplay implements Observer
{
    private float temperature;
    private float humidity;
    private WeatherData weatherData;

    public CurrentConditionsDisplay(WeatherData weatherData)
    {
        this.weatherData = weatherData;
        weatherData.registerObserver(this);
    }

    @Override
    public void update(float temperature, float humidity, float pressure)
    {
        this.temperature = temperature;
        this.humidity = humidity;
        display();
    }

    public void display()
    {
        System.out.printf("Сейчас значения: %.1f градусов цельсия и %.1f %% влажности\n", temperature, humidity);
    }
}


class CurrentConditionsDisplayEn implements Observer
{
    private float temperature;
    private float humidity;
    private WeatherData weatherData;

    public CurrentConditionsDisplayEn(WeatherData weatherData)
    {
        this.weatherData = weatherData;
        weatherData.registerObserver(this);
    }

    @Override
    public void update(float temperature, float humidity, float pressure)
    {
        this.temperature = temperature;
        this.humidity = humidity;
        display();
    }

    public void display()
    {
        System.out.printf("Current value: %.1f celsia and %.1f %% humidity\n", temperature, humidity);
    }
}