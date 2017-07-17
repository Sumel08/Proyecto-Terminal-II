function [ words, en, zr, n ] = wordframe( signal, fs, frame, overlap, threshold )
%UNTITLED Summary of this function goes here
%   Detailed explanation goes here

    % Número de muestras por frame
    frameN = fs*frame;

    % Número de muestras del traslape
    overlapN = fs*overlap;
    
    % Valor para n
    
    wpp = 1/(2*frameN);
    
    en = [];
    zr = [];
    memV = [];
    words = [];
    flagWhile = 1;
    indexBegin = 1;

    while (flagWhile)
        enA = 0;
        zrA = 0;
        memV = [];
        for i=indexBegin:indexBegin+frameN-1
            enA = enA + signal(i)*signal(i);
            zrA = zrA + abs(signo(signal(i+1))-signo(signal(i)));
            auxM = memV;
            memV = [auxM signal(i)];

            if (i==length(signal)-1)
                flagWhile = 0;
                break;
            end
        end
        if ((enA*zrA) > threshold)
            auxO = words;
            words = [auxO memV(1:frameN-overlapN)];
            disp(1);
        else
            disp(0);
        end
        auxEn = en;
        en = [auxEn enA];
        auxSgn = zr;
        zr = [auxSgn zrA];
        indexBegin = i - overlapN;
        %flagWhile = 0;
    end

    n = 1:length(en);
end

