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
  coords?: ICoordinates
}

export interface ICoordinates {
  lon: number,
  lat: number
}

export interface IMetroStationCorrespondence {
  station: IMetroStation,
  correspondence: string[]
}
