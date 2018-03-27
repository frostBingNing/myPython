import sys
import pygame
from bullet import Bullet
from alien import Alien
def check_keydown_events(event,ai_settings,screen,ship,bullets):
    if event.key == pygame.K_RIGHT:
        ship.moving_right = True
    elif event.key == pygame.K_LEFT:
        ship.moving_left = True
    elif event.key == pygame.K_SPACE:
        if len(bullets) < ai_settings.bullet_allowed :
           new_bullet = Bullet(ai_settings,screen,ship)
           bullets.add(new_bullet)
    elif event.key == pygame.K_q:
        sys.exit()
def check_keyup_events(event,ship):
    if event.key == pygame.K_RIGHT:
        ship.moving_right = False
    elif event.key == pygame.K_LEFT:
        ship.moving_left = False
def check_events(ai_settings,screen,ship,bullets):
    for event in pygame.event.get():
        if event.type == pygame.QUIT :
            sys.exit()
        elif event.type == pygame.KEYUP:
            check_keyup_events(event,ship)
        elif event.type == pygame.KEYDOWN:
            check_keydown_events(event,ai_settings,screen,ship,bullets)
def update_screen(ai_settings,screen,ship,aliens,bullets):
    screen.fill(ai_settings.bg_color)
    for bullet in bullets.sprites():
        bullet.draw_bullet()
    for bullet in bullets:
        bullet.draw_bullet()
    ship.blitme()
    aliens.draw(screen)
    pygame.display.flip()
def update_bullets(ai_settings,scrren,ship,aliens,bullets):
    bullets.update()
    for bullet in bullets.copy():
        if bullet.rect.bottom <= 0:
            bullets.remove(bullet)
    collisions = pygame.sprite.groupcollide(aliens,bullets,True,True)
    if len(aliens) == 0 :
        bullets.empty()
        create_fleet(ai_settings,scrren,ship,aliens)
def get_number_rows(ai_settings,ship_height,alien_height):
    available_space_y = ai_settings.screen_height - 3*(alien_height)- ship_height
    number_rows = int(available_space_y/(2*alien_height))
    return number_rows
def get_number_alien_x(ai_settings, alien_width):
    available_space_x = ai_settings.screen_width - 2 * alien_width
    number_aliens_x = int(available_space_x / (2 * alien_width))
    return(number_aliens_x)
def create_alien(ai_settings,screen,aliens,alien_number,number_row):
    alien = Alien(ai_settings, screen)
    alien_width = alien.rect.width
    alien.x = alien_width + 2 * alien_number * alien_width
    alien.rect.x = alien.x
    alien.rect.y =  alien.rect.height + 2*alien.rect.height*number_row
    alien.y = alien.rect.y
    aliens.add(alien)
def create_fleet(ai_settings,screen,ship,aliens):
    alien = Alien(ai_settings,screen)
    number_aliens_x = get_number_alien_x(ai_settings,alien.rect.width)
    number_rows = get_number_rows(ai_settings,ship.rect.height,alien.rect.height)
    for number_row in range(number_rows):
        for alien_number in range(number_aliens_x):
            create_alien(ai_settings,screen,aliens,alien_number,number_row)
def check_edges(ai_settings,aliens):
    for alien in aliens.sprites():
        if alien.check_edges():
            change_fleet_direction(ai_settings,aliens)
            break;
def change_fleet_direction(ai_settings,aliens):
    for alien in aliens.sprites():
        alien.rect.y += ai_settings.fleet_down
    ai_settings.fleet_direction *= -1
def update_aliens(ai_settings,aliens):
    check_edges(ai_settings,aliens)
    aliens.update()
