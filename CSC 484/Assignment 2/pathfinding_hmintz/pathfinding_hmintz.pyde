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
            
            
