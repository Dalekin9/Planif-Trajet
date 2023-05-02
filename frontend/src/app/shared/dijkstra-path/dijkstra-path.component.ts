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
  public startingTime: string = '';
  @Input() bestTimePath: boolean = true;

  constructor() {
  }

  ngOnInit(): void {
  }

  @Input('path')
  public set path(path: IDijkstraPathGroup[]) {
    this._path = path;
    this.expanded = new Array(this._path.length).fill(false);
  }

  @Input('startTime') set startTime(startingTime: string) {
    this.startingTime = moment(startingTime, 'hh:mm:ss A').format('hh:mm:ss A');
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
    const startingTime = previousNode.nodes[previousNode.nodes.length - 1].weight;
    return this.getTimeForNode(startingTime);
  }

  public getEndTimeForNode(index: number): string {
    const node = this._path[index];
    const endTime = node.nodes[node.nodes.length - 1].weight;
    return this.getTimeForNode(endTime);
  }

  public getDuration(): string {
    const endTime = moment();
    endTime.seconds(0);
    endTime.hour(0);
    endTime.minutes(0);
    const lastNode = this._path[this._path.length - 1];
    endTime.add(lastNode.nodes[lastNode.nodes.length - 1].weight, 'seconds');
    const startTime = moment(this.startingTime, 'hh:mm:ss A');
    const diffHours = endTime.diff(startTime, 'hours');
    const diffMinutes = endTime.diff(startTime.add(diffHours, 'hours'), 'minutes');
    const diffSeconds = endTime.diff(startTime.add(diffMinutes, 'minutes'), 'seconds');
    if (!!diffHours) {
      return `${diffHours}:${diffMinutes}:${diffSeconds}`;
    }
    return `${diffMinutes}:${diffSeconds}`;
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
