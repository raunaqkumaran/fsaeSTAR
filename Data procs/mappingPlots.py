import pandas as pd
import scipy.ndimage
import matplotlib.pyplot as plt


def getvals(file, title):
    return pd.read_csv(file, usecols=[title], sep=',').T.values.tolist()[0]


def getlistlist(listadder, maxdata):
    grid = []
    templist = []
    for i in range(0, maxdata):
        templist.append(frh[i])
        templist.append(rrh[i])
        templist.append(listadder[i])
        grid.append(list(templist))
        templist.clear()
    return grid


fileName = "2019 Simulation Results - Sensitivity.csv"


Cl = getvals(fileName, 'Cl * A (m^2)')
frontBal = getvals(fileName, 'Balance (front)')
Cd = getvals(fileName, 'Cd * A (m^2)')
LD = getvals(fileName, 'L/D')
frh = getvals(fileName, 'Front Ride height (inches delta)')
rrh = getvals(fileName, 'Rear ride height (inches delta)')

dataCount = len(Cl)

x = frh
y = rrh
z = Cd


fig, downforcePlot = plt.subplots(nrows=1)

downforcePlot.tricontour(x, y, z, levels=20, linewidths=0.5, colors='k')
cntr2 = downforcePlot.tricontourf(x, y, z, levels=14, cmap="RdBu_r")

fig.colorbar(cntr2)
downforcePlot.axis((-1, 1, -1, 1))

plt.subplots_adjust(hspace=0.5)
plt.xlabel('Front ride height')
plt.ylabel('Rear ride height')
plt.title('Drag')
plt.show()


print("End")
