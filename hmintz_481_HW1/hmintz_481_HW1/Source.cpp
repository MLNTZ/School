#include <SFML/Graphics.hpp>
#include <string>
#include <iostream>
/* This class will represent a platform and contain its position and velocity*/



/*This class defines a static platform, it only has a size and position*/
class Platform : public sf::RectangleShape {
public:
    explicit Platform(sf::Vector2f size, sf::Vector2f pos)
    {
        setSize(size);
        setPosition(pos);
        setFillColor(sf::Color::White);

    }

};

/* This class will be a platform that moves left to right on the screen*/
class MovingPlatform : public sf::RectangleShape {
public:
    /*if dir is 0 then the platform is moving right, else it it moving left*/
    int dir;
    int speed;
    explicit MovingPlatform(sf::Vector2f size, sf::Vector2f pos) {
        setSize(size);
        setPosition(pos);
        setFillColor(sf::Color::White);
        dir = 0;
        speed = 10;

    }

    void travel(int len) {
        sf::Vector2f loc = getPosition();
        sf::Vector2f size = getSize();
        if (loc.x + size.x >= len) {
            dir = 1;
        }
        if (loc.x <= 0) {
            dir = 0;
        }


        if (dir == 0) {
            move(speed, 0);
        }
        else {
            move(-speed, 0);
        }
    }
};

/*This class defines a character who can move witht the pushing of the the arrow keys as well as junp with space
   The character can check collisions with other objects and change its velocity accordingly*/
class Character : public sf::CircleShape {
public:
    sf::Vector2f acc;
    sf::Vector2f velo;
    float speed;
    int gravity;
    int jumping;
    int drag;
    int onTop;

    explicit Character(float size, sf::Vector2f pos) {
        setRadius(size);
        setPosition(pos);
        setFillColor(sf::Color(100, 250, 50));
        setOrigin(size, size);
        speed = 10;
        gravity = 2;
        drag = 2;
        acc = sf::Vector2f(0, 0);
        velo = sf::Vector2f(0, 0);
        jumping = 0;
        onTop = 0;


    }

    void travel(int len, int height) {
       /* if the location of the bottom of the screen we are on the ground*/
        if (getPosition().y + getRadius() < height && jumping == 1) {
            velo.y += gravity;
        }
        if (getPosition().y + getRadius() >= height) {
            setPosition(getPosition().x, height - getRadius());
            jumping = 0;
            velo.y = 0;
        }
        if (sf::Keyboard::isKeyPressed(sf::Keyboard::Space) && jumping == 0 )
        {
            jumping = 1;
            velo.y -= speed * 2;
        }
        if (sf::Keyboard::isKeyPressed(sf::Keyboard::Left))
        {
            velo.x = -speed;
        }
        if (sf::Keyboard::isKeyPressed(sf::Keyboard::Right))
        {
            velo.x = speed;
        }
        if (velo.x > 0) {
            velo.x -= drag;
        }
        else if (velo.x < 0){
            velo.x += drag;
        }
        if ((velo.x < 0 && getPosition().x - getRadius() <= 0) || (velo.x > 0 && getPosition().x + getRadius() >= len)) {
            velo.x = 0;
        }


        move(velo);

    }


    void collide(sf::RectangleShape obj) {
        int intersect = 0;
        if (obj.getGlobalBounds().intersects(getGlobalBounds())) {
            intersect = 1;
            sf::Vector2f norm = obj.getPosition() - getPosition();
            if (norm.y < 0) {
                velo = sf::Vector2f(-norm.x / 4, -norm.y / 4);
            }
            else {
                velo.y = 0;
                 jumping = 0;
                 onTop = 1;
            }
           
        }
        if (intersect == 0 && onTop == 1) {
            onTop = 0;
        }
        else if (onTop == 0){
            jumping = 1;
        }


    }
};









int main()
{
    sf::RenderWindow window(sf::VideoMode(800, 600) ,"hmintz HW1", sf::Style::Close);
    Platform shape(sf::Vector2f(240.f, 35.f), sf::Vector2f(100.f, 400.f));
    MovingPlatform plat(sf::Vector2f(120.f, 20.f), sf::Vector2f(500.f, 500.f));
    Character character(15, sf::Vector2f(100.f, 585.f));
    while (window.isOpen())
    {
        sf::Event event;
        while (window.pollEvent(event))
        {
            if (event.type == sf::Event::Closed)
                window.close();
        }
        sf::Texture tex;
        sf::Texture tex2;
        tex.loadFromFile("PlatformTexture.jpg");
        tex2.loadFromFile("tex1.jpg");
        shape.setTexture(&tex); 
        plat.setTexture(&tex2);

        window.clear();
        window.draw(plat);
        plat.travel(800);
        window.draw(shape);
        character.travel(800, 600);
        character.collide(plat);
        character.collide(shape);
        window.draw(character);
        window.display();
    }

    return 0;
}
