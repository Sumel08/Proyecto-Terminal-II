clear all
close all

[y1, fs1] = audioread('Grabaciones/Hola_Dulce_Bancas_Woman.wav');
[y2, fs2] = audioread('Grabaciones/Hola_8_8000_Elisa1_Casa_Woman.wav');
[y3, fs3] = audioread('Grabaciones/Hola_8_8000_Oscar1_Casa_Man.wav');
[y4, fs4] = audioread('Grabaciones/Hola_8_8000_Gustavo_315_Man.wav');
[y5, fs5] = audioread('Grabaciones/Hola_8_8000_Guadalupe1_casa_Woman.wav');

[y6, fs6] = audioread('Grabaciones/Farmacia_Dulce_Bancas_Woman.wav');
[y7, fs7] = audioread('Grabaciones/Farmacia_8_8000_Elisa1_Casa_Woman.wav');
[y8, fs8] = audioread('Grabaciones/Farmacia_8_8000_Oscar1_Casa_Man.wav');
[y9, fs9] = audioread('Grabaciones/Farmacia_8_8000_Gustavo_315_Man.wav');
[y10, fs10] = audioread('Grabaciones/Farmacia_8_8000_Guadalupe1_casa_Woman.wav');

[y11, fs11] = audioread('Grabaciones/Información_Dulce_Bancas_Woman.wav');
[y12, fs12] = audioread('Grabaciones/Información_8_8000_Elisa1_Casa_Woman.wav');
[y13, fs13] = audioread('Grabaciones/Información_8_8000_Oscar1_Casa_Man.wav');
[y14, fs14] = audioread('Grabaciones/Información_8_8000_Gustavo_315_Man.wav');
[y15, fs15] = audioread('Grabaciones/Información_8_8000_Guadalupe2_casa_Woman.wav');

[y16, fs16] = audioread('Grabaciones/Noches_Dulce_Bancas_Woman.wav');
[y17, fs17] = audioread('Grabaciones/Noches_8_8000_Elisa1_Casa_Woman.wav');
[y18, fs18] = audioread('Grabaciones/Noches_8_8000_Oscar1_Casa_Man.wav');
[y19, fs19] = audioread('Grabaciones/Noches_8_8000_Gustavo_315_Man.wav');
[y20, fs20] = audioread('Grabaciones/Noches_8_8000_Guadalupe2_casa_Woman.wav');

% sound(y1,fs1)
% pause
% sound(y2,fs2) 

% Coeficiente filtro preenfasis
a = 0.95;

%y = y2';
y = preemphasis(y2, a);
fs = fs1;

% Tamaño del frame en segundos
frame = 0.05;

% Traslape en segundos
overlap = 0.01;

% Número de muestras por frame
frameN = fs*frame;

% Número de muestras del traslape
overlapN = fs*overlap;

% Valor para n
n = 0:frameN-1;
wpp = 1/(2*frameN);

% Umbral de energía
thresholdEn = 2;

enV = [];
sgnV = [];
memV = [];
output = [];
flagWhile = 1;
indexBegin = 1;

while (flagWhile)
    en = 0;
    sgn = 0;
    memV = [];
    for i=indexBegin:indexBegin+frameN-1
        en = en + y(i)*y(i);
        sgn = sgn + abs(signo(y(i+1))-signo(y(i)));
        auxM = memV;
        memV = [auxM y(i)];
        
        if (i==length(y)-1)
            flagWhile = 0;
            break;
        end
    end
    if (en > thresholdEn)
        auxO = output;
        output = [auxO memV(1:frameN-overlapN)];
    end
    auxEn = enV;
    enV = [auxEn en];
    auxSgn = sgnV;
    sgnV = [auxSgn sgn];
    indexBegin = i - overlapN;
    %flagWhile = 0;
end

%disp(enV);

ref = 1:length(enV);

sumV = enV+sgnV;

sound(output,fs);
%sound(y,fs);
%sound(preemphasis(output, a), fs);

outW = hammingWin(output, frameN, overlapN);

subplot(2,1,1)
plot(y)
subplot(2,1,2)
plot(ref,enV,ref,sgnV,ref,sumV)

figure
plot(output);

figure
subplot(2,1,1)
plot(output);
subplot(2,1,2)
plot(preemphasis(output, a));