package edu.iis.mto.oven;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class OvenTest {
    @Mock
    private HeatingModule heatingModule;
    @Mock
    private Fan fan;
    private Oven oven;

//    @BeforeAll
//    void testInit() {
//        Mockito.when(fan.on())
//    }
    @Test
    void shouldTurnOnFanTwoTimesWhenProgramOfTypeThermoCirculationIsCoolAtFinish() {
        //given
        oven = new Oven(heatingModule, fan);
        List<ProgramStage> programStages = new ArrayList<>();
        programStages.add(ProgramStage.builder()
                .withTargetTemp(180)
                .withStageTime(20)
                .withHeat(HeatType.THERMO_CIRCULATION)
                .build());
        BakingProgram bakingProgram = BakingProgram.builder()
                .withInitialTemp(80)
                .withStages(programStages)
                .withCoolAtFinish(true)
                .build();

        //when
        oven.runProgram(bakingProgram);

        //then
        Mockito.verify(fan, Mockito.times(2)).on();
    }

    @Test
    void shouldTurnOnFanWhenProgramOfTypeNotThermoCirculationIsCoolAtFinish() {
        //given
        oven = new Oven(heatingModule, fan);
        List<ProgramStage> programStages = new ArrayList<>();
        programStages.add(ProgramStage.builder()
                .withTargetTemp(180)
                .withStageTime(20)
                .withHeat(HeatType.GRILL)
                .build());
        BakingProgram bakingProgram = BakingProgram.builder()
                .withInitialTemp(80)
                .withStages(programStages)
                .withCoolAtFinish(true)
                .build();

        //when
        oven.runProgram(bakingProgram);

        //then
        Mockito.verify(fan, Mockito.times(1)).on();
    }

    @Test
    void shouldTurnOffFanWhenProgramOfTypeNotThermoCirculationHasFanTurnOn() {
        //given
        oven = new Oven(heatingModule, fan);
        List<ProgramStage> programStages = new ArrayList<>();
        programStages.add(ProgramStage.builder()
                .withTargetTemp(180)
                .withStageTime(20)
                .withHeat(HeatType.HEATER)
                .build());
        BakingProgram bakingProgram = BakingProgram.builder()
                .withInitialTemp(80)
                .withStages(programStages)
                .withCoolAtFinish(true)
                .build();
        Mockito.when(fan.isOn()).thenReturn(true);

        //when
        oven.runProgram(bakingProgram);

        //then
        Mockito.verify(fan, Mockito.times(1)).off();
    }

//    @Test
//    void shouldThrowOvenExceptionWhenHeaterModuleThrowHeatingException() throws HeatingException {
//        //given
//        oven = new Oven(heatingModule, fan);
//        List<ProgramStage> programStages = new ArrayList<>();
//        programStages.add(ProgramStage.builder()
//                .withTargetTemp(180)
//                .withStageTime(20)
//                .withHeat(HeatType.HEATER)
//                .build());
//        BakingProgram bakingProgram = BakingProgram.builder()
//                .withInitialTemp(80)
//                .withStages(programStages)
//                .withCoolAtFinish(true)
//                .build();
//        HeatingSettings heatingSettings = HeatingSettings.builder()
//                .withTargetTemp(20)
//                .withTimeInMinutes(60)
//                .build();
//
//        Mockito.when(heatingModule.heater(Mockito.any(HeatingSettings.class))).doThrow(HeatingException.class);
//
//        //when
//        oven.runProgram(bakingProgram);
//
//        //then
//        Mockito.verify(fan, Mockito.times(1)).off();
//    }

    @Test
    void shouldRunHeatingProgramGrill() throws HeatingException {
        //given
        oven = new Oven(heatingModule, fan);
        List<ProgramStage> programStages = new ArrayList<>();
        programStages.add(ProgramStage.builder()
                .withTargetTemp(180)
                .withStageTime(20)
                .withHeat(HeatType.GRILL)
                .build());
        BakingProgram bakingProgram = BakingProgram.builder()
                .withInitialTemp(80)
                .withStages(programStages)
                .withCoolAtFinish(true)
                .build();
        HeatingSettings heatingSettings = HeatingSettings.builder()
                .withTargetTemp(20)
                .withTimeInMinutes(60)
                .build();

        //when
        oven.runProgram(bakingProgram);

        //then
        Mockito.verify(heatingModule, Mockito.times(1)).grill(heatingSettings);
    }

}
