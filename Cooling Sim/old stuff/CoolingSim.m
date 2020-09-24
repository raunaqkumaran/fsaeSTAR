%% Header
%%% Code by Alex Krueger 2019 / John Foster 2018

close all
clear
clc

%% %% Load Data
load('Data.mat') %Grissom simulated endurance data
% Ambient temp 23.7°C
% Cold_T_rej is engine inlet coolant temperature in °C
% Hot_T_rej is engine outlet coolant temperature in °C
% time is time in seconds
% fan_on, pump_on are binary signals
% wheel_speed_kmh is wheel speed in km/h

%% Inputs

%Radiator
hCore = 8; %in Height of core
wCore = 6; %in Width of core
dCore = 1.26; %in Depth of core
NumFins = 787; %Number of fins per meter
NumTubes = 8; %Number of tubes
wTubes = 0.002; %m Width of tube

%Pump
mdotWaterx = .25; %kg/s Water mass flow, pump on

%Airflow
T_amb = 90; %F Air inlet temp (Ambient Temp) (Worst case 95)
Given_Mdot_Air = .11; %From Aero CFD at 15 m/s 
Fan_mdotAir = 2*[.15 .19 .21 .24 .29 .35]; %kg/s CalPoly model, Corresponding mass flow rates through rad

%% Define Constants
CPWater = 4.2118; %kJ/kg*K Specific heat of water around 85 Degrees C
CPAir = 1.0062; %kJ/kg*K Specific heat of air around 25 C at 1 atm
CPAl = .91; %kJ/kg*K Specific heat of Al
mColdWater = 2.03; %kg Cold side Water
mHotWater = .57; %kg Hot side Water
mAl = 30; %kg Al
mdotwater_rej = .3; %kg/s Water mass flow for engine heat rejection test, do not change
Qdot_eng_off = 2.856; %kW Heat transfer when engine is off
Rho_a = 1.18; %air density, kg/m^3

U = .065; %kW/m^2*K Overall heat transfer coefficient (This is what you tune)

%% Radiator SA Calculations
hCore = hCore*25.4/1000; %m Height of core
wCore = wCore*25.4/1000; %m Width of core
dCore = dCore*25.4/1000; %m Depth of core
A_front = hCore*wCore; %frontal area, m^2

saTubes = (2*hCore*dCore*NumTubes)+(hCore*NumTubes*wTubes*2); %m^2 Surface area of tubes
saFins = 2*NumFins*hCore*dCore*(wCore - (NumTubes*wTubes)); %m^2 Surface area of fins
A = saTubes + saFins; %m^2 Gas-side radiator surface area

%% Define Variables
wheel_spd_avg = movmean(wheel_speed_kmh,11); %km/h Filters vehicle speed
dt = time(2) - time(1); %s Simulation time step
dT = Hot_T_rej - Cold_T_rej; %K Temperature gain across engine
Veh_v = [0 10 15 25 40 60]; %m/s Vehicle speeds used for Airflows
Veh_v_kmph = Veh_v*3.6; %kmph Vehicle speeds used for interpolation
Cold_T(1) = Cold_T_rej(1); %°C Initial engine-in coolant temp
Hot_T(1) = Hot_T_rej(1); %°C Initial engine-out coolant temp
for i = 1:6
    CFD_mdotAir(i) = Given_Mdot_Air*Veh_v(i)/15; %kg/s CalPoly Model, Corresponding mass flow rates through rad
end

%% Engine Heat Rejection
for i = 1:length(time)
    if i == 1 || Hot_T_rej(i) == 0 || Hot_T_rej(i-1) == 0
        Qdot_eng(i) = CPWater*mdotwater_rej*dT(i); %kW Engine heat rejection
    else
        Qdot_eng(i) = CPWater*mdotwater_rej*dT(i) + mHotWater*CPWater*(Hot_T_rej(i)-Hot_T_rej(i-1))/dt; %kW Engine heat rejection
    end
end

%% Air Mass Flow Rate / Fan Maths

syms mdot dP; %dP in Pa, mdot in kg/s

for i = 1:length(time)
    Free_mdotAir(i) = interp1(Veh_v_kmph,CFD_mdotAir,wheel_spd_avg(i),'linear','extrap'); %kg/s Mass flow rate of air through rad (only ram air)
    if Free_mdotAir(i) < 0.0001
        Free_mdotAir(i) = 0.0001; %Do this so nothing breaks
    end
    
    Face_v = Free_mdotAir(i)/Rho_a/A_front;
    P_Veh = 17.613*.5*Rho_a*Face_v^2;
    Fan_Reg = mdot == -0.0006*(dP - P_Veh) + 0.1993; %fan regression from datasheet
    Rad_Reg = mdot == Rho_a*A_Front*(5.514*((dP*0.0034)^.6)); %radiator regression from CR data for 787 fins, 32 mm core
    
    S = solve([Fan_Reg Rad_Reg],[mdot dP]); %Solve system
    
    Forced_mdotAir(i) = min(double(S.mdot)); %Min needed for the .6 power in the regresion
end

%% Radiator Heat Rejection
T_amb = (T_amb-32)*5/9; %F to C

for i = 1:length(time)-1
    
    % FAN LOGIC
    if Hot_T(i)>90 %fan on
        mdotAir(i) = Forced_mdotAir(i);
    else %fan off
        mdotAir(i) = Free_mdotAir(i);
    end
    
    % Variable Reset
    mdotWater=mdotWaterx;
    CWater=CPWater*mdotWater; %Cmax
    CAir=CPAir*mdotAir(i); %Cmin
    NTU = (U*A)/CAir; %Number of transfer units, Cair because it is Cmin
    Cratio = (CAir/CWater); %Heat capacity ratio
    
    % Q_MAX
    QdotMax = CAir*(Hot_T(i) - T_amb); %kW Maximum heat transfer possible per radiator during worst possible enviornment

    % QDOT_RAD
    %Epsilon = 1 - exp(((1/Cratio)*(NTU^0.22))*(exp(-Cratio*(NTU^0.78))-1)); %BOTH UNMIXED Radiator Effectiveness, from textbook, cross flow single pass unmixed fluids
    Epsilon = 1 - exp(-(1/Cratio)*(1-exp(-Cratio*NTU))); %AIR MIXED Radiator Effectiveness, from textbook, cross flow single pass unmixed fluids
    Qdot_Rad(i) = -1*Epsilon*QdotMax; %kW Heat rejected per second by rad(s), multiply by number of rads.
    
    Qdot_Rad(i) = Qdot_Rad(i)*2; %%%%%FOR DUAL RAD%%%%%
    
    % Pump On/Off
    if ~pump_on(i) %HT off
         mdotWater = 0;
    end
    
    % Hot Side
       Hot_T(i+1) = (Qdot_eng(i)*dt/(mHotWater*CPWater)) + (mdotWater*dt/mHotWater)*(Cold_T(i) - Hot_T(i)) + Hot_T(i); %°C Coolant temp out for next time step (energy balance)
    % Cold Side
       Cold_T(i+1) = (Qdot_Rad(i)*dt/((mColdWater*CPWater)+(mAl*CPAl))) + (mdotWater*CPWater*dt/((mColdWater*CPWater)+(mAl*CPAl)))*(Hot_T(i) - Cold_T(i)) + Cold_T(i); %°C Coolant temp in for next time step (energy balance)
end

%% Plots
plots_on = 1; %hides or shows plots (0=plots hidden, 1=plots shown)

if plots_on
    figure
    subplot(4,1,1)
    plot(time,Hot_T_rej,'r')
    hold on
    plot(time,Hot_T,'b')
    ylabel('Engine Temp [°C]')
    legend('projected data','recorded data','Location','southeast') 

    subplot(4,1,2)
    %plot(time,Hot_T_rej-Cold_T_rej,'r')   
    hold on
    plot(time,Hot_T-Cold_T,'b')
    ylabel('Engine Outlet-Inlet Temp [°C]')
    legend('projected data','recorded data','Location','southeast') 

    subplot(4,1,3)
    plot(time(1:23781),Qdot_Rad','b')
    ylabel('Qdot Radiator [kW]')
    xlabel('Time [s]')
    legend('projected data') 
    
    subplot(4,1,4)
    %plot(time,Cold_T_rej,'r')    
    hold on
    plot(time,Cold_T,'b')
    ylabel('Engine Temp [°C]')
    legend('projected data','recorded data','Location','southeast') 
end

disp('Max Temperature [°C]:')
disp(max(Hot_T))