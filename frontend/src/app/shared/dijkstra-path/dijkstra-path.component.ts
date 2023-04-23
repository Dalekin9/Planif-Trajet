import {Component, Input, OnInit} from '@angular/core';
import {IDijkstraPathGroup, mapLinesColors} from "../../types/dtos";
import * as moment from "moment";

@Component({
  selector: 'app-dijkstra-path',
  templateUrl: './dijkstra-path.component.html',
  styleUrls: ['./dijkstra-path.component.scss']
})
export class DijkstraPathComponent implements OnInit {
  private _path: IDijkstraPathGroup[] = [];
  public expanded: boolean[] = [];
  @Input() startingTime: string = '';

  constructor() {
  }

  ngOnInit(): void {
  }

  @Input('path')
  public set path(path: IDijkstraPathGroup[]) {
    this._path = path;
    this.expanded = new Array(this._path.length).fill(false);
  }

  public get path(): IDijkstraPathGroup[] {
    return this._path;
  }

  onHeaderClick(event) {
  }

  onDotClick(event) {
  }

  onExpandEntry(expanded: boolean, index: number) {
    this.expanded[index] = expanded;
  }

  public getLineColor(name: string): string {
    return mapLinesColors.get(name);
  }

  public getStartTimeForNode(index: number): string {
    if (index == 0) {
      return this.startingTime;
    }
    const previousNode = this._path[index - 1];
    const startingTime = previousNode.nodes[previousNode.nodes.length - 1].duration;
    return this.getTimeForNode(startingTime);
  }

  public getEndTimeForNode(index: number): string {
    const node = this._path[index];
    const endTime = node.nodes[node.nodes.length - 1].duration;
    return this.getTimeForNode(endTime);
  }

  private getTimeForNode(seconds: number): string {
    const midnight = moment()
    midnight.seconds(0);
    midnight.hour(0);
    midnight.minutes(0);
    midnight.add(seconds, 'seconds');
    return midnight.format('hh:mm:ss A');
  }
}
