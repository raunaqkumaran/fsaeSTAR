close all
clear
clc

A_f = 0.0309677; %frontal area, m^2xxxxxx
A_fR = 0.0367; %frontal area, m^2xxxxxxxxxx
rho_a = 1.18; %air density, kg/m^3xxxxxxxxxx

syms mdot dP; %dP in Pa, mdot in kg/s

vehSpd = [0 10 15 25 40 60]; %vehicle speed in m/sxxxxxxxxxxx

for i = 1:6
    
      freeflowM=vehSpd(i)*.3/30;%xxxxxxxxxxxx
      FaceV=freeflowM/rho_a/A_f;
      
      Pveh = (17.613*.5*rho_a*FaceV^2); %Pressure drop from vehicle speed, from CFD+ math
      
      eqn1 = mdot == -0.0006*(dP - Pveh) + 0.1993; %fan regression
      eqn2 = mdot == rho_a*A_f*(5.514*((dP*0.0034)^.6)); %radiator regression fits best?
%     eqn2 = mdot == rho_a*A_f*(5.514*((Pveh*0.0034)^.6)); %radiator regression fits best?
%     eqn2 = mdot == rho_a*A_f*(0.0122*(dP) + 1.5562); %radiator regression
%     eqn2 = mdot == rho_a*A_f*(-10e-5*(dP)^2 + 0.0194*(dP) + 0.8298); %radiator regression
    
    S = solve([eqn1 eqn2],[mdot dP]);
    
    %mdot_out(i) = max(double(S.mdot)); %For almost all rad regressions 
    mdot_out(i) = min(double(S.mdot)); %For the regression with ^.6
end

figure(1)
plot(vehSpd,mdot_out)
xlim([0 60])
ylim([0 0.6])

figure(2)
eqn1 = rho_a*A_f*(5.514*((dP*0.0034)^.6)); %radiator regression
eqn2 = -0.0006*dP + 0.1993; %fan regression
eqn3 = -0.0006*(dP-51.7) + 0.1993; %fan regression
eqn4 = -0.0006*(dP-116.5) + 0.1993; %fan regression
eqn5 = -0.0006*(dP-323.61) + 0.1993; %fan regression
eqn6 = -0.0006*(dP-828.44) + 0.1993; %fan regression
eqn7 = -0.0006*(dP-1864) + 0.1993; %fan regression
fplot(eqn1)
hold on
fplot(eqn2)
fplot(eqn3)
fplot(eqn4)
fplot(eqn5)
fplot(eqn6)
fplot(eqn7)
xlim([0 1500])
ylim([0 0.6])