<<<<<<< Updated upstream
import json
locations = []
obsticals = []
knightLoc = PVector()
target = PVector()
graph = []

def setup():
    size(640, 480)
    frameRate(60)
    global knightLoc, obsticals, locations, graph, target
    knightLoc, locations, obsticals = readShapes();
    graph = createGraph()
    target = PVector(334, 116)
    find(graph , knightLoc, target)

def draw():

    drawKnight()
    drawLocs()
    drawObs()
    
    
def createGraph():
    ret = createGraphics(width, height)
    ret.beginDraw()
    for ob in obsticals:
        name = ob[0]
        # col = getColor(name)
        points = ob[1]
        ret.fill(0)
        ret.beginShape()
        for p in points:
            ret.vertex(p.x, p.y)
        ret.endShape(CLOSE)
    ret.endDraw();
    ret.loadPixels()
    return ret;
    
def drawKnight():
    img = loadImage("knight.png")
    img.resize(20, 25)
    image(img, knightLoc[0], knightLoc[1])
    
def drawLocs():
    for loc in locations:
        name = loc[0] + ".png"
        img = loadImage(name)
        img.resize(35, 35)
        image(img, loc[1], loc[2])
        
def drawObs():
    ob = obsticals[0]
    for ob in obsticals:
        name = ob[0]
        col = getColor(name)
        points = ob[1]
        fill(col)
        beginShape()
        for p in points:
            vertex(p.x, p.y)
        endShape(CLOSE)
        
def getColor(name):
    if ("mountain" in name):
        return color(120)
    if("lake" in name):
        return color(0, 0, 255)
    if("forest" in name):
        return color(0, 255, 0)
    return color(120, 120, 0)
        
        
def readShapes():
    with open("world.json", "r") as read_file:
        data = json.load(read_file)
    
    start = data.get("knight_start")
    knight = PVector(start[0], start[1])
    
    keyLocs = data.get("key_locations")
    key_locations = []
    for val in keyLocs:
        place = keyLocs.get(val)
        ar = [str(val) , place[0], place[1]]
        key_locations.append(ar)
        
    obsticals = []
    obs = data.get("obstacles")
    
    for val in obs:
        ob = obs.get(val)
        points = []
        for point in ob:
            points.append(PVector(point[0], point[1]))
        obsticals.append([str(val), points])

    return knight, key_locations, obsticals


    


def find(graph, start, target):
path = []
loc = start
bestDir = PVector()
bestPoint = loc
surround = getSurround(loc)
legal = []
for point in surround:
    newLoc = PVector(int(point.x), int(point.y))
    if graph.get(newLoc.x, newLoc.y) == 0:
        legal.append(point)
        lastDist = PVector.dist(loc, target)
        nextDist = PVector.dist(newLoc, target)
        if (nextDist < lastDist):
            bestPoint = newLoc
    
    
    



def getSurround(pos):
    speed = 5
    ret = []
    ret.append(PVector.add(pos, PVector(1, -1).setMag(speed)))
    ret.append(PVector.add(pos,PVector(0, 1).setMag(speed)))
    ret.append(PVector.add(pos,PVector(-1, 0).setMag(speed)))
    ret.append(PVector.add(pos,PVector(1, 0).setMag(speed)))
    ret.append(PVector.add(pos,PVector(-1, -1).setMag(speed)))
    ret.append(PVector.add(pos,PVector(-1, 1).setMag(speed)))
    ret.append(PVector.add(pos,PVector(1, -1).setMag(speed)))
    ret.append(PVector.add(pos,PVector(1, 1).setMag(speed)))
    for pl in ret:
        if pl.x > width or pl.x < 0 or pl.y > height or pl.y < 0:
            ret.remove(pl)
            
    return ret
            
            
=======
import json
from array import *
import Queue as queue
locations = []
obsticals = []
knightLoc = PVector()
target = PVector()
grid = []

def setup():
    size(640, 480)
    frameRate(60)
    global knightLoc, obsticals, locations, grid, target
    knightLoc, locations, obsticals = readShapes();
    graph = createGraph()
    grid = Grid(width/ 8, height / 8)
    grid.fillObs(graph)
    target = PVector(181, 401)

def draw():
    drawKnight()
    drawLocs()
    drawObs()
    
    grid.drawGrid()

 
    
def createGraph():
    ret = createGraphics(width, height)
    ret.beginDraw()
    for ob in obsticals:
        name = ob[0]
        # col = getColor(name)
        points = ob[1]
        ret.fill(0)
        ret.beginShape()
        for p in points:
            ret.vertex(p.x, p.y)
        ret.endShape(CLOSE)
    ret.endDraw();
    ret.loadPixels()
    return ret;
    
def drawKnight():
    img = loadImage("knight.png")
    img.resize(20, 25)
    image(img, knightLoc[0], knightLoc[1])
    
def drawLocs():
    for loc in locations:
        name = loc[0] + ".png"
        img = loadImage(name)
        img.resize(35, 35)
        image(img, loc[1], loc[2])
        
def drawObs():
    ob = obsticals[0]
    for ob in obsticals:
        name = ob[0]
        col = getColor(name)
        points = ob[1]
        fill(col)
        noStroke()
        beginShape()
        for p in points:
            vertex(p.x, p.y)
        endShape(CLOSE)
        
def getColor(name):
    if ("mountain" in name):
        return color(120)
    if("lake" in name):
        return color(0, 0, 255)
    if("forest" in name):
        return color(0, 255, 0)
    return color(120, 120, 0)
        
        
def readShapes():
    with open("world.json", "r") as read_file:
        data = json.load(read_file)
    
    start = data.get("knight_start")
    knight = PVector(start[0], start[1])
    
    keyLocs = data.get("key_locations")
    key_locations = []
    for val in keyLocs:
        place = keyLocs.get(val)
        ar = [str(val) , place[0], place[1]]
        key_locations.append(ar)
        
    obsticals = []
    obs = data.get("obstacles")
    
    for val in obs:
        ob = obs.get(val)
        points = []
        for point in ob:
            points.append(PVector(point[0], point[1]))
        obsticals.append([str(val), points])

    return knight, key_locations, obsticals


def mouseClicked():
    grid.grab(mouseX, mouseY).colSwap
    










class Grid():
    def __init__(self, xCount=640, yCount=480):
        self.xblock = xCount;
        self.yblock = yCount;
        
        self.xlen = width / xCount
        self.ylen = height / yCount
        
        self.grid = [Block()] * xCount * yCount
        self.fillGrid();
        
    def fillGrid(self):
        for y in range(0, self.yblock):
            for x in range(0, self.xblock):
                ind = self.xblock * y + x
                self.grid[ind] = Block(self.xlen, self.ylen, x, y)
                
    def drawGrid(self):
       for y in range(0, self.yblock):
            for x in range(0, self.xblock):
                 ind = self.xblock * y + x
                 self.grid[ind].drawBlock()
    def grab(self, x, y):
        xind = int(x / self.xlen)
        yind = int (y / self.ylen)
        
        if (xind < 0 or xind >= self.xblock or yind < 0 or yind >= self.yblock):
            return None
        else:
            return self.grid[self.xblock * yind + xind]
        
    def ind(self, x, y):
        ind =  self.xblock * y + x
        return grid[ind]
        
    def fillObs(self, graph):
        for y in range(0, self.yblock):
            for x in range(0, self.xblock):
                 ind = self.xblock * y + x
                 if self.grid[ind].containsObs(graph):
                      self.grid[ind].colSwap()
        
class Block():
    def __init__(self, xlen=0, ylen=0, x=0, y=0, col=-1):
        self.xlen = xlen
        self.ylen = ylen
        self.x = x
        self.y = y
        self.topLeft = PVector(x * xlen, y * ylen)
        self.col = col
    
    def drawBlock(self):
        # noStroke()
        if (self.col == -1):
            noFill()
        else:
            fill(self.col)
        rect(self.topLeft.x, self.topLeft.y, self.xlen, self.ylen)
        
    def colSwap(self):
        if (self.col == -1):
            self.col = 0;
        else:
            self.col = -1
            
    def containsObs(self, graph):
        xind = int( self.x * self.xlen)
        yind = int(self.y * self.ylen)
        
        xrang = range(xind, xind + self.xlen)
        yrange = range(yind, yind + self.ylen)
        
        for i in yrange:
            for j in xrang:
               if (graph.get(j, i) != 0):
                   return True
        
        return False
    
    def getneighbors(self, grid):
        ret = []
        ret.append(grid.ind(self.x + 1 , self.y))
        ret.append(grid.ind(self.x - 1 , self.y))
        ret.append(grid.ind(self.x   , self.y + 1))
        ret.append(grid.ind(self.x  , self.y - 1))
        
        return ret;
        
                
            
            
            
            
                        
>>>>>>> Stashed changes
