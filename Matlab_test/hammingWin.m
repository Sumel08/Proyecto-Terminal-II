function [ output ] = hammingWin( signal, fs, frameW, frameO )
%UNTITLED5 Summary of this function goes here
%   Detailed explanation goes here

    % Número de muestras por frame
    frameN = fs*frameW;

    % Número de muestras del traslape
    overlapN = fs*frameO;

n = 0:frameN-1;

wN = 0.54-0.46.*cos((2*pi.*n)/(frameN-1));

flagWhile = 1;
indexBegin = 1;
indexWindow = 1;
output = [];
L = length(signal);
M = floor((L-frameN)/(frameN-overlapN)+1);             % number of frames 

% figure out if the input vector can be divided into frames exactly
E = (L-((M-1)*(frameN - overlapN)+frameN));

if (E>0)
    % how much padding will be needed to complete the last frame?
    P = M*(frameN-overlapN) + frameN - L;
    
    signal = [signal 1E-6*randn(1,P)];
end

disp('longitud')
disp(length(signal));

while (flagWhile)
    indexWindow = 1;
    memV = [];
%     disp('i1');
%     disp(indexBegin);
    for i=indexBegin:indexBegin+frameN-1
        auxM = memV;
        memV = [auxM signal(i)*wN(indexWindow)];
        indexWindow = indexWindow + 1;
        
        if (i==length(signal))
            flagWhile = 0;
            break;
        end
    end
%     disp('i2');
%     disp(i);
    auxO = output;
    
%     disp('longs');
%     disp(size(auxO));
%     disp(size(memV));
    
    output = [auxO;memV];
    indexBegin = i - overlapN + 1;
    %flagWhile = 0;
end


end

