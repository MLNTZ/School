import matplotlib.pyplot as plt
from matplotlib import style
import yfinance as yf
import pandas as pd
from pandas_datareader import data as pdr
from alpha_vantage.timeseries import TimeSeries as AV
from alpha_vantage.techindicators import TechIndicators as TI
from yahoo_fin.stock_info import *

import datetime
import time
## 1. open 2. high 3. low 4. close 5.volume


pd.set_option('display.max_columns', 500)
#This will return the intra day pricing information on the given stock
#interval can be 1min, 5min, 60min
def get_intraday(symbol='GOLD', outputsize ="full", interval='1min'):
    key = '3M9W90LUTRRVK830'
    av = AV(key=key, output_format='pandas')
    intra =av.get_intraday(symbol=symbol, interval=interval, outputsize=outputsize)
    return intra[0]
#this will return the daily pricing history of the given stock
#size='compact' will return past 100 days
def get_daily(symbol='GOLD', size="full"):
    key = '3M9W90LUTRRVK830'
    av = AV(key=key, output_format='pandas')
    data, meta_data = av.get_daily(symbol, outputsize=size)
    return  data
#will return the current quote information from the given stock
def get_quote(symbol='GOLD'):
    key = '3M9W90LUTRRVK830'
    av = AV(key=key, output_format='pandas')
    # data, meta_data = av.get_daily(symbol, outputsize=size)
    quote = av.get_quote_endpoint(symbol='GOLD')
    return float(quote[0]['05. price'].array[0])
#grabs the sma information from the API
#interval is dist between data points
#time period is the number of points to include in average
def get_sma(symbol='GOLD', size="full", interval='1min', timeperiod=5, series='close'):
    key = '3M9W90LUTRRVK830'
    ti = TI(key=key, output_format='pandas')

    ret = ti.get_sma(symbol=symbol,  interval=interval, time_period=timeperiod, series_type=series)
    return ret[0];
#this manualy calculates the Simple Moving Average of the given data
#uses the interval of the data sent in
#window defines number of data points to include
def calc_sma(data, window=5, series='4. close'):
    rev = data[series][::-1]
    sma = rev.rolling(window=window, min_periods=0).mean()
    return sma
#overly complicated whay to filter out data by the stupid Time Index that pandas uses
#this is rlly slow
def filterByTimeIndex(data, year=None, month=None, day=None):
    if(year != None):
        ret = data[data.index.year == year]
    if(month != None):
        ret = ret[ret.index.month == month]
    if(day != None):
        ret = ret[ret.index.day >= day]
    return ret

def locate_crosses(this, other):
    crossVal = []
    crossInd = []
    ma1 = this[::-1]
    ma2 = other[::-1]
    c = len(ma1)
    for i in range(2, c):
        test1 = ma1[0:i][::-1]
        test2 = ma2[0:i][::-1]
        if(crossing(test1, test2)):
            crossVal.append(test1[0])
            crossInd.append(test1.index[0])
    return pd.DataFrame(index=crossInd, data=crossVal)

def crossing(this, other):
    #define other crossing this upwards
    if (other[0] > this[0] and other[1] < this[1]):
        print("Other is crossing this upwards at", other.index[0])
        return True;
    #define other crossing this downwards
    if(other[0] < this[0] and other[1] > this[1]):
        print("Other is crossing downwards at", other.index[0])
        return True
    return False

#data = get_intraday()
data = get_intraday()


data['ma1'] = calc_sma(data, window=200)
data['ma2'] = calc_sma(data, window=15)



# data = filterByTimeIndex(data, year=2020, month=3, day=2)
data.to_csv("up_to_3.2.20_GOLD")

#SNAPSHOT OF 3:29 TODAY
# data = data[data.index.hour == 15]
# data = data[data.index.minute <= 29]
# crossing(data['ma1'], data['ma2'])

crosses = locate_crosses(data['ma1'], data['ma2'])





# plot the pricing data
ax1 = plt.subplot2grid((5,1), (0,0), rowspan=5, colspan=1)
ax1.plot(data.index, data['4. close'], label='Gold Price')
ax1.plot(data.index, data['ma1'], label='200 SMA' )
ax1.plot(data.index, data['ma2'], label='15 SMA' )
ax1.scatter(crosses.index, crosses[0], s=100, label='Crosses')

plt.legend()
# ax1.plot(data.index, data['100ma'])
#
# ax2.plot(data.index, d'a'ta['5. volume'])

plt.show()
#
#track()




;