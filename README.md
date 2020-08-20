# Event domain

## Notes

* Listener of domain events are :
    * Application Services
    * Exposition Resources classes (to enjoy Spring and proxy management)

* Domain events are published from domain classes
    * Is it always true ?

## Questions

* Should a DomainEvent be handle in the same transaction ?
    * Depends on business ? What are the rules ?
* What happens if a service listening for events throws an Exception ? 
    * Should it rollback everything ?
    * Should it be skipped ? 
    * Depends on business requirements ?
* Should an event treated asynchronously ?
* Is it a good practice to publish an event in the aggregate constructor ? 
    * should it be before / after save ? Depends on what criteria ? 