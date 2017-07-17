function [ output ] = preemphasis( signal, a )
%UNTITLED4 Summary of this function goes here
%   Detailed explanation goes here

    output = zeros([1,length(signal)-1]);

    for i=1:length(signal)-1
        output(i) = signal(i+1)-a*signal(i);
    end

end

