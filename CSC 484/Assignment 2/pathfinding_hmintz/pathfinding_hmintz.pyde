import json
from array import *
import Queue as queue
import heapq
import collections
locations = []
obsticals = []
knightLoc = PVector()
target = PVector()
grid = []
path = {}
searching = False
trail = []
gridScale = 8


class PriorityQueue:
    def __init__(self):
        self.elements = []
    
    def empty(self):
        return len(self.elements) == 0
    
    def put(self, item, priority):
        heapq.heappush(self.elements, (priority, item))
    
    def get(self):
        return heapq.heappop(self.elements)[1]
    
    
def setup():
    size(640, 480)
    frameRate(60)
    global knightLoc, obsticals, locations, grid, target, searching, path
    found = False
    knightLoc, locations, obsticals = readShapes();
    graph = createGraph()
    grid = Grid(width/ gridScale, height / gridScale)
    grid.fillObs(graph)
   

    # kn = grid.grab(knightLoc.x, knightLoc.y)
    # n = kn.getNeighbors(grid)
    # for i in n:
    #     i.colSwap()

def mouseClicked():
    target = PVector(mouseX, mouseY)
    global searching, trail
    searching = True
    path, cost = find(grid, knightLoc, target)
    if path == None:
        searching = False;
        print("I cant go there")
    else : trail = build_path(grid, path, knightLoc, target)

def draw():
    global knightLoc
    background(255)
    
    drawLocs()
    drawObs()
    drawKnight()
    # grid.drawGrid()
    if (searching and len(trail) > 0):
        next = trail[0]
        trail.remove(next)
        if (len(trail) == 0):                
            found = False
        knightLoc = next.topLeft
        

    
def build_path(grid, path, knightLoc, target):
    ret = []
    targ = grid.grab(target.x, target.y)
    start = grid.grab(knightLoc.x, knightLoc.y)
    cur = targ
    next = None
    ret.insert(0, cur)
    while(cur != start):
        next = path[cur]
        ret.insert(0, cur)
        cur = next
    ret.insert(0, cur)
    return ret
    
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
    imageMode(CENTER)
    img = loadImage("knight.png")
    img.resize(grid.xlen * 2, grid.ylen * 2)
    image(img, knightLoc[0], knightLoc[1])
    
def moveKnight(next):
    knightLoc = next
    
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






def find(grid, start, target):
    startBlock = grid.grab(start.x, start.y)
    targBlock = grid.grab(target.x, target.y)
    if (targBlock.col == 0):
        return None, None
    front = PriorityQueue()
    front.put(startBlock, 0)
    path = {}
    cost = {}
    path[startBlock] = None
    cost[startBlock] = 0
    found = False
    
    while not front.empty():
        cur = front.get()
        if (cur != None and cur.col != 0):
            if(cur.x == targBlock.x and cur.y == targBlock.y):
                found = True
                break
            for next in cur.getNeighbors(grid):
                new_cost = cost[cur] + 1
                if next not in cost or new_cost < cost[next]:
                    cost[next] = new_cost
                    h = + heuristic(startBlock, targBlock)
                    dx1 = cur.x - targBlock.x
                    dy1 = cur.y - targBlock.y
                    dx2 = startBlock.x - targBlock.x
                    dy2 = startBlock.y - targBlock.y
                    cr = abs(dx1*dy2 - dx2*dy1)
                    h = h + cr*.001
                    prio = new_cost + h
                    front.put(next, prio)
                    path[next] = cur;   
    if targBlock not in path:
        print('oof')
        return None, None
        
    return path, cost
    


def heuristic(cur, targ):
    (x1, y1) = (cur.x, cur.y)
    (x2, y2) = (targ.x, targ.y)
    return abs(x1 - x2) + abs(y1- y2)



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
        try:
            ind =  self.xblock * y + x
            return self.grid[ind]
            break
        except:
            return None
        
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
        stroke(0)
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
    
    def getNeighbors(self, grid):
        ret = []
        ret.append(grid.ind(self.x + 1 , self.y))
        ret.append(grid.ind(self.x + 1 , self.y + 1))
        ret.append(grid.ind(self.x + 1 , self.y - 1))
        ret.append(grid.ind(self.x - 1 , self.y))
        ret.append(grid.ind(self.x - 1 , self.y - 1))
        ret.append(grid.ind(self.x   , self.y + 1))
        ret.append(grid.ind(self.x - 1 , self.y + 1))
        ret.append(grid.ind(self.x  , self.y - 1))
        
        return ret;
        
                
            
