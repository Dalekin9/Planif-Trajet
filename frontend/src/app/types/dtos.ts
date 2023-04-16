export interface IDummyData {}

export interface IMetro {
  name: string,
  stations?: IMetroStation[],
  schedules?: IMetroSchedule[]
}

export interface IMetroSchedule {
  line: string,
  terminus: string,
  schedules: number[]
}

export interface IMetroStation {
  name: string,
  longitude: number,
  latitude: number
}

export interface IMetroStationCorrespondence {
  station: IMetroStation,
  metroLines: string[]
}
