import matplotlib.pyplot as plt
from matplotlib import style
import yfinance as yf
import pandas as pd
from pandas_datareader import data as pdr
from alpha_vantage.timeseries import TimeSeries as AV
from alpha_vantage.techindicators import TechIndicators as TI
from yahoo_fin.stock_info import *
## 1. open 2. high 3. low 4. close 5.volume

def get_daily(symbol='GOLD', size="full"):
    key = '3M9W90LUTRRVK830'
    av = AV(key=key, output_format='pandas')
    data, meta_data = av.get_daily(symbol, outputsize=size)
    quote = av.get_quote_endpoint(symbol='GOLD')
    return data, quote

data, quote = get_daily()
data.to_csv('gold.csv')
#data = pd.read_csv('tsla.csv', parse_dates=True, index_col=0)

live = get_live_price('GOLD')
print(live)
print(quote)

# key = '3M9W90LUTRRVK830'
# tech = TI(key=key, output_format='pandas')
# rsi = tech.get_rsi('TSLA', interval='60min', time_period=10, series_type='close')
#
# print(rsi.head())
data['100ma'] = data['4. close'].rolling(window=50, min_periods=0).mean()
#
ax1 = plt.subplot2grid((8,1), (0,0), rowspan=5, colspan=1)
ax2 = plt.subplot2grid((8,1), (6,0), rowspan=2, colspan=1)
ax1.plot(data.index, data['4. close'])
ax1.plot(data.index, data['100ma'])

ax2.plot(data.index, data['5. volume'])

#
#
plt.show()



