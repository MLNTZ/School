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

def get_intraday(symbol='GOLD', outputsize ="full", interval='1min'):
    key = '3M9W90LUTRRVK830'
    av = AV(key=key, output_format='pandas')
    intra =av.get_intraday(symbol=symbol, interval=interval, outputsize=outputsize)
    return intra[0]

def get_daily(symbol='GOLD', size="full"):
    key = '3M9W90LUTRRVK830'
    av = AV(key=key, output_format='pandas')
    data, meta_data = av.get_daily(symbol, outputsize=size)
    return  data

def get_quote(symbol='GOLD'):
    key = '3M9W90LUTRRVK830'
    av = AV(key=key, output_format='pandas')
    # data, meta_data = av.get_daily(symbol, outputsize=size)
    quote = av.get_quote_endpoint(symbol='GOLD')
    return float(quote[0]['05. price'].array[0])

def get_sma(symbol='GOLD', size="full", interval='1min', timeperiod=5, series='close'):
    key = '3M9W90LUTRRVK830'
    ti = TI(key=key, output_format='pandas')

    ret = ti.get_sma(symbol=symbol,  interval=interval, time_period=timeperiod, series_type=series)
    return ret[0];

def calc_sma(data, window=5, series='4. close'):
    rev = data[series][::-1]
    sma = rev.rolling(window=window, min_periods=0).mean()
    return sma

def filterByTimeIndex(data, year=2020, month=3, day=2):
    if(year != None):
        ret = data[data.index.year == year]
    if(month != None):
        ret = ret[ret.index.month == month]
    if(day != None):
        ret = ret[ret.index.day == day]
    return ret



#data, quote = get_daily()
#data.to_csv('gold.csv')

data = pd.read_csv('gold.csv', parse_dates=True, index_col=0)



data = get_intraday()


data['ma1'] = calc_sma(data, window=200)
data['ma2'] = calc_sma(data, window=3)



data = filterByTimeIndex(data)

#
# data = data[data.index.str.contains('2020-03-02')]




# key = '3M9W90LUTRRVK830'
# tech = TI(key=key, output_format='pandas')
# rsi = tech.get_rsi('TSLA', interval='60min', time_period=10, series_type='close')
#
# print(rsi.head())

# #
ax1 = plt.subplot2grid((5,1), (0,0), rowspan=5, colspan=1)
# ax2 = plt.subplot2grid((8,1), (6,0), rowspan=2, colspan=1)
ax1.plot(data.index, data['4. close'], label='Gold Price')
ax1.plot(data.index, data['ma1'], label='200 SMA' )
ax1.plot(data.index, data['ma2'], label='15 SMA' )


plt.legend()
# ax1.plot(data.index, data['100ma'])
#
# ax2.plot(data.index, data['5. volume'])

plt.show()
#
#track()




