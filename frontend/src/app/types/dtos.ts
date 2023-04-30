export interface IDummyData {}

export interface IMetro {
  name: string,
  stations?: INode[],
  schedules?: IMetroSchedule[]
}

export interface IMetroSchedule {
  line: string,
  terminus: string,
  schedules: number[]
}

export interface INode {
  name: string,
  longitude: number,
  latitude: number
}

export interface IMetroLineStationSchedules {
  metroLine: string,
  station: string,
  schedules: number[]
}

export interface IMetroStationCorrespondence {
  station: INode,
  metroLines: string[]
}

export interface IDijkstraSearchResult {
  start: INode,
  end: INode,
  weight: number,
  metroLine?: string,
  terminusStation?: string
}

export interface IDijkstraPathGroup {
  metroLine?: string,
  terminusStation?: string,
  nodes: IDijkstraSearchResult[]
}

export const mapLinesColors = new Map<string, string>([
  ["1", "#ffcd07"],
  ["2", "#006db8"],
  ["3", "#9b993a"],
  ["3B", "#87d3df"],
  ["4", "#bb499b"],
  ["5", "#f6904b"],
  ["6", "#76c695"],
  ["7", "#f69fb4"],
  ["7B", "#84c28e"],
  ["8", "#c5a3cd"],
  ["9", "#cec929"],
  ["10", "#e0b03c"],
  ["11", "#8d6639"],
  ["12", "#008c5a"],
  ["13","#87d3df"],
  ["14", "#662d91"]
]);
