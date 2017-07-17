clear all
close all

% [y, fs] = audioread('Grabaciones/Hola_Dulce_Bancas_Woman.wav');
% [y, fs] = audioread('Grabaciones/Hola_8_8000_Elisa1_Casa_Woman.wav');
% [y, fs] = audioread('Grabaciones/Hola_8_8000_Oscar8_Casa_Man.wav');
% [y, fs] = audioread('Grabaciones/Hola_8_8000_Gustavo_315_Man.wav');
% [y, fs] = audioread('Grabaciones/Hola_8_8000_Guadalupe1_casa_Woman.wav');
% 
% [y, fs] = audioread('Grabaciones/Farmacia_Dulce_Bancas_Woman.wav');
% [y7, fs7] = audioread('Grabaciones/Farmacia_8_8000_Elisa1_Casa_Woman.wav');
% [y, fs] = audioread('Grabaciones/Farmacia_8_8000_Oscar2_Casa_Man.wav');
% [y, fs] = audioread('Grabaciones/Farmacia_8_8000_Gustavo_315_Man.wav');
% [y, fs] = audioread('Grabaciones/Farmacia_8_8000_Guadalupe3_casa_Woman.wav');
% 
% [y, fs] = audioread('Grabaciones/Información_Dulce_Bancas_Woman.wav');
% [y, fs] = audioread('Grabaciones/Información_8_8000_Elisa1_Casa_Woman.wav');
% [y, fs] = audioread('Grabaciones/Información_8_8000_Oscar5_Casa_Man.wav');
% [y, fs] = audioread('Grabaciones/Información_8_8000_Gustavo_315_Man.wav');
 [y, fs] = audioread('Grabaciones/Información_8_8000_Guadalupe3_casa_Woman.wav');
% 
% [y, fs] = audioread('Grabaciones/Noches_Dulce_Bancas_Woman.wav');
% [y, fs] = audioread('Grabaciones/Noches_8_8000_Elisa1_Casa_Woman.wav');
% [y, fs] = audioread('Grabaciones/Noches_8_8000_Oscar1_Casa_Man.wav');
% [y, fs] = audioread('Grabaciones/Noches_8_8000_Gustavo_315_Man.wav');
% [y, fs] = audioread('Grabaciones/Noches_8_8000_Guadalupe3_casa_Woman.wav');
%
% [y, fs] = audioread('Grabaciones/A_Dulce_Bancas_Woman.wav');
% [y, fs] = audioread('Grabaciones/A_8_8000_Elisa1_Casa_Woman.wav');
% [y, fs] = audioread('Grabaciones/A_8_8000_Oscar1_Casa_Man.wav');
% [y, fs] = audioread('Grabaciones/A_8_8000_Gustavo_315_Man.wav');
% [y, fs] = audioread('Grabaciones/A_8_8000_Guadalupe3_casa_Woman.wav');
%
% [y, fs] = audioread('Grabaciones/Acepta_Dulce_Bancas_Woman.wav');
% [y, fs] = audioread('Grabaciones/Acepta_8_8000_Elisa1_Casa_Woman.wav');
% [y, fs] = audioread('Grabaciones/Acepta_8_8000_Oscar2_Casa_Man.wav');
% [y, fs] = audioread('Grabaciones/Acepta_8_8000_Gustavo_315_Man.wav');
% [y, fs] = audioread('Grabaciones/Acepta_8_8000_Guadalupe3_casa_Woman.wav');

% y = y8;
% fs = fs8;

wordF = 0.04;
wordO = 0.02;

threshold = 40;
a = 0.95;
 
frameW = 0.04;
frameO = 0.02;

tic
% [words, en, zr, n] = wordframe(y, fs, wordF, wordO, threshold);
% [filtered] = preemphasis(words, a);
% [framed] = hammingWin(filtered, fs, frameW, frameO);
[filtered] = preemphasis(y, a);
[words, en, zr, n] = wordframe(filtered, fs, wordF, wordO, threshold);
[framed] = hammingWin(words, fs, frameW, frameO);
% algo = fft(framed);
toc

 sound(y, fs);
 pause;
 sound(words, fs);

figure
subplot(4,1,1)
plot(y)
axis([0 length(y) min(y) max(y)]);
subplot(4,1,2)
plot(n,en)
axis([0 length(n) min(en) max(en)]);
subplot(4,1,3)
plot(n,zr)
axis([0 length(n) min(zr) max(zr)]);
subplot(4,1,4)
plot(words)
axis([0 length(words) min(words) max(words)]);

figure
subplot(2,1,1)
plot(words)
subplot(2,1,2)
plot(filtered)